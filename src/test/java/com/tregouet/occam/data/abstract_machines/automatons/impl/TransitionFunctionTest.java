package com.tregouet.occam.data.abstract_machines.automatons.impl;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.alg.builders.representations.concept_trees.ConceptTreeBuilder;
import com.tregouet.occam.alg.builders.representations.productions.impl.IfIsAThenBuildProductions;
import com.tregouet.occam.alg.scoring_dep.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring_dep.ScoringStrategy_dep;
import com.tregouet.occam.alg.transition_function_gen_dep.impl.TransitionFunctionSupplier;
import com.tregouet.occam.data.languages.words.construct.IConstruct;
import com.tregouet.occam.data.languages.words.fact.ICompositeEdgeProduction;
import com.tregouet.occam.data.languages.words.fact.ISimpleEdgeProduction;
import com.tregouet.occam.data.languages.words.fact.IStronglyContextualized;
import com.tregouet.occam.data.languages.words.fact.impl.BlankEdgeProduction;
import com.tregouet.occam.data.logical_structures.automata.IAutomaton;
import com.tregouet.occam.data.logical_structures.automata.machines.deprec.Automaton_dep;
import com.tregouet.occam.data.logical_structures.automata.machines.utils.ScoreThenCostTFComparator;
import com.tregouet.occam.data.logical_structures.automata.states.IState;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.IOperator;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.ITransition;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IDenotation;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.concepts.impl.ConceptLattice;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.InvertedTree;

@SuppressWarnings("unused")
public class TransitionFunctionTest {

	private static final Path SHAPES = Paths.get(".", "src", "test", "java", "files", "shapes1bis.txt");
	private static List<IContextObject> objects;
	private IConceptLattice conceptLattice;
	private DirectedAcyclicGraph<IDenotation, IStronglyContextualized> denotations = 
			new DirectedAcyclicGraph<>(null, null, false);
	private ConceptTreeBuilder conceptTreeBuilder;
	private DirectedAcyclicGraph<IDenotation, IStronglyContextualized> filtered_reduced_denotations;
	private IHierarchicalRestrictionFinder<IDenotation, IStronglyContextualized> denotationTreeSupplier;
	private InvertedTree<IDenotation, IStronglyContextualized> denotationTree;
	private TreeSet<IAutomaton> automatons;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		objects = GenericFileReader.getContextObjects(SHAPES);	
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy_dep.SCORING_STRATEGY_1);
	}

	private static double binaryLogarithm(double arg) {
		return Math.log10(arg)/Math.log10(2);
	}
	
	private static boolean sameSourceAndTargetCategoryForAll(List<IStronglyContextualized> stronglyContextualizeds) {
		boolean sameSourceAndTargetDenotationSet = true;
		IConcept sourceDS = null;
		IConcept targetDS = null;
		for (int i = 0 ; i < stronglyContextualizeds.size() ; i++) {
			if (i == 0) {
				sourceDS = stronglyContextualizeds.get(i).getSourceConcept();
				targetDS = stronglyContextualizeds.get(i).getTargetConcept();
			}
			else {
				if (!stronglyContextualizeds.get(i).getSourceConcept().equals(sourceDS)
						|| !stronglyContextualizeds.get(i).getTargetConcept().equals(targetDS))
					sameSourceAndTargetDenotationSet = false;
			}
		}
		return sameSourceAndTargetDenotationSet;
	}
	
	private static boolean sameTargetAttributeAsOneOtherProduction(IStronglyContextualized stronglyContextualized, List<IStronglyContextualized> stronglyContextualizeds) {
		if (stronglyContextualizeds.size() == 1)
			return true;
		boolean sameTargetAttributeAsOneOther = false;
		List<IStronglyContextualized> others = new ArrayList<>(stronglyContextualizeds);
		others.remove(stronglyContextualized);
		for (IStronglyContextualized other : others) {
			if (stronglyContextualized.getTarget().equals(other.getTarget()))
				sameTargetAttributeAsOneOther = true;
		}
		return sameTargetAttributeAsOneOther;
	}
	
	private static boolean sameValueAsOneOtherProduction(IStronglyContextualized stronglyContextualized, List<IStronglyContextualized> stronglyContextualizeds) {
		if (stronglyContextualizeds.size() == 1)
			return true;
		boolean sameValue = false;
		List<IStronglyContextualized> others = new ArrayList<>(stronglyContextualizeds);
		others.remove(stronglyContextualized);
		for (IStronglyContextualized other : others) {
			List<IConstruct> valuesOfOther = new ArrayList<>(other.getValues());
			if (valuesOfOther.removeAll(stronglyContextualized.getValues()))
				sameValue = true;
		}
		return sameValue;
	}
	
	@Before
	public void setUp() throws Exception {
		automatons = new TreeSet<>(ScoreThenCostTFComparator.INSTANCE);
		conceptLattice = new ConceptLattice(objects);
		List<IStronglyContextualized> stronglyContextualizeds = new IfIsAThenBuildProductions(conceptLattice).getProductions();
		stronglyContextualizeds.stream().forEach(p -> {
			denotations.addVertex(p.getSource());
			denotations.addVertex(p.getTarget());
			denotations.addEdge(p.getSource(), p.getTarget(), p);
		});
		conceptTreeBuilder = conceptLattice.getConceptTreeSupplier();
		while (conceptTreeBuilder.hasNext()) {
			InvertedTree<IConcept, IIsA> currTreeOfDenotationSets  = conceptTreeBuilder.next();
			filtered_reduced_denotations = 
					TransitionFunctionSupplier.getDenotationGraphFilteredByTreeOfDenotationSets(
							currTreeOfDenotationSets, denotations);
			denotationTreeSupplier = new RestrictorOpt<>(filtered_reduced_denotations, true);
			while (denotationTreeSupplier.hasNext()) {
				denotationTree = denotationTreeSupplier.nextTransitiveReduction();
				IAutomaton automaton = 
						new Automaton_dep(currTreeOfDenotationSets, denotationTree);
				automatons.add(automaton);
			}
		}
	}
	
	@Test
	public void when2NonBlankProductionsHaveSameSourceAndTargetDenotSetsAndSameValueThenHandledBySameOperator() 
			throws IOException {
		boolean sameOperator = true;
		int checkCount = 0;
		for (IAutomaton tF : automatons) {
			/*
			System.out.println(tF.getDomainSpecificLanguage().toString());
			Visualizer.visualizeTransitionFunction(tF, "2108251050_tf", TransitionFunctionGraphType.FINITE_AUTOMATON);
			*/
			List<ISimpleEdgeProduction> basicProds = new ArrayList<>();
			List<IOperator> basicProdsOperators = new ArrayList<>();
			for (ITransition transition : tF.getTransitions()) {
				if (transition instanceof IOperator) {
					IOperator operator = (IOperator) transition;
					for (IStronglyContextualized stronglyContextualized : operator.operation()) {
						if (stronglyContextualized instanceof BlankEdgeProduction) {
						}
						else if (stronglyContextualized instanceof ISimpleEdgeProduction) {
							basicProds.add((ISimpleEdgeProduction) stronglyContextualized);
							basicProdsOperators.add(operator);
						}
						else {
							ICompositeEdgeProduction compoProd = (ICompositeEdgeProduction) stronglyContextualized;
							for (ISimpleEdgeProduction basicProd : compoProd.getComponents()) {
								basicProds.add(basicProd);
								basicProdsOperators.add(operator);
							}
						}
					}
				}
			}
			for (int i = 0 ; i < basicProds.size() - 1 ; i++) {
				for (int j = i + 1 ; j < basicProds.size() ; j++) {
					ISimpleEdgeProduction iProd = basicProds.get(i);
					ISimpleEdgeProduction jProd = basicProds.get(j);
					if (iProd.getSourceConcept().equals(jProd.getSourceConcept())
							&& iProd.getTargetConcept().equals(jProd.getTargetConcept())
							&& iProd.getValue().equals(jProd.getValue())) {
						checkCount++;
						if (!basicProdsOperators.get(basicProds.indexOf(iProd)).equals(
								basicProdsOperators.get(basicProds.indexOf(jProd))))
							sameOperator = false;
					}
				}
			}
		}
		assertTrue(sameOperator == true && checkCount > 0);
	}

	@Test 
	public void when2ProductionsHaveSameSourceDenotSetAndSameTargetAttributeThenHandledBySameOperator() {
		boolean sameOperator = true;
		int checkCount = 0;
		for (IAutomaton tF : automatons) {
			Map<IStronglyContextualized, IOperator> prodToOpe = new HashMap<>();
			for (ITransition transition : tF.getTransitions()) {
				if (transition instanceof IOperator) {
					IOperator operator = (IOperator) transition;
					for (IStronglyContextualized stronglyContextualized : operator.operation()) {
						prodToOpe.put(stronglyContextualized, operator);
					}
				}
			}
			List<IStronglyContextualized> stronglyContextualizeds = new ArrayList<>(prodToOpe.keySet());
			for (int i = 0 ; i < stronglyContextualizeds.size() - 1 ; i++) {
				for (int j = i + 1 ; j < stronglyContextualizeds.size() ; j++) {
					IStronglyContextualized iProd = stronglyContextualizeds.get(i);
					IStronglyContextualized jProd = stronglyContextualizeds.get(j);
					if (iProd.getSourceConcept().equals(jProd.getSourceConcept())
							&& iProd.getTarget().equals(jProd.getTarget())) {
						checkCount++;
						if (!prodToOpe.get(iProd).equals(prodToOpe.get(jProd)))
							sameOperator = false;						
					}
				}
			}
		}
		assertTrue(checkCount > 0 && sameOperator);
	}
	
	@Test
	public void whenProductionsHandledBySameOperatorThenConsistentWithRequirements() {
		boolean consistent = true;
		for (IAutomaton tF : automatons) {
			for (ITransition transition : tF.getTransitions()) {
				if (transition instanceof IOperator) {
					IOperator operator = (IOperator) transition;
					List<IStronglyContextualized> stronglyContextualizeds = operator.operation();
					if (!sameSourceAndTargetCategoryForAll(stronglyContextualizeds))
						consistent = false;
					for (IStronglyContextualized stronglyContextualized : stronglyContextualizeds) {
						if (!sameTargetAttributeAsOneOtherProduction(stronglyContextualized, stronglyContextualizeds) 
								&& !sameValueAsOneOtherProduction(stronglyContextualized, stronglyContextualizeds))
							consistent = false;
					}
				}
			}
		}
		assertTrue(consistent);
	}
	
	@Test
	public void whenTransitionFunctionGraphRequestedThenReturned() throws IOException {
		boolean graphReturned = true;;
		for (IAutomaton tF : automatons) {
			SimpleDirectedGraph<IState, IConjunctiveTransition> graph = null;
			try {
				graph = tF.getFiniteAutomatonGraph();
			}
			catch (Exception e) {
				graphReturned = false;
			}
			if (graph == null)
				graphReturned = false;
		}
		assertTrue(graphReturned);
	}

}
