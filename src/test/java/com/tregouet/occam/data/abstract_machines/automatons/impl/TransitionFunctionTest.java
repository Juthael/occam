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

import com.tregouet.occam.alg.denotation_sets_gen.IDenotationSetsTreeSupplier;
import com.tregouet.occam.alg.scoring.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring.ScoringStrategy;
import com.tregouet.occam.alg.transition_function_gen.impl.ProductionBuilder;
import com.tregouet.occam.alg.transition_function_gen.impl.TransitionFunctionSupplier;
import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.abstract_machines.automatons.impl.Automaton;
import com.tregouet.occam.data.abstract_machines.automatons.utils.ScoreThenCostTFComparator;
import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transition_rules.IConjunctiveTransition;
import com.tregouet.occam.data.abstract_machines.transition_rules.IOperator;
import com.tregouet.occam.data.abstract_machines.transition_rules.ITransitionRule;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IDenotationSets;
import com.tregouet.occam.data.denotations.IContextObject;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.occam.data.denotations.impl.DenotationSets;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.specific.ISimpleEdgeProduction;
import com.tregouet.occam.data.languages.specific.ICompositeEdgeProduction;
import com.tregouet.occam.data.languages.specific.IEdgeProduction;
import com.tregouet.occam.data.languages.specific.impl.BlankEdgeProduction;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

@SuppressWarnings("unused")
public class TransitionFunctionTest {

	private static final Path SHAPES = Paths.get(".", "src", "test", "java", "files", "shapes1bis.txt");
	private static List<IContextObject> objects;
	private IDenotationSets denotationSets;
	private DirectedAcyclicGraph<IDenotation, IEdgeProduction> denotations = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IDenotationSetsTreeSupplier denotationSetsTreeSupplier;
	private DirectedAcyclicGraph<IDenotation, IEdgeProduction> filtered_reduced_denotations;
	private IHierarchicalRestrictionFinder<IDenotation, IEdgeProduction> denotationTreeSupplier;
	private Tree<IDenotation, IEdgeProduction> denotationTree;
	private TreeSet<IAutomaton> automatons;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		objects = GenericFileReader.getContextObjects(SHAPES);	
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy.SCORING_STRATEGY_1);
	}

	private static double binaryLogarithm(double arg) {
		return Math.log10(arg)/Math.log10(2);
	}
	
	private static boolean sameSourceAndTargetCategoryForAll(List<IEdgeProduction> edgeProductions) {
		boolean sameSourceAndTargetDenotationSet = true;
		IDenotationSet sourceDS = null;
		IDenotationSet targetDS = null;
		for (int i = 0 ; i < edgeProductions.size() ; i++) {
			if (i == 0) {
				sourceDS = edgeProductions.get(i).getSourceDenotationSet();
				targetDS = edgeProductions.get(i).getTargetDenotationSet();
			}
			else {
				if (!edgeProductions.get(i).getSourceDenotationSet().equals(sourceDS)
						|| !edgeProductions.get(i).getTargetDenotationSet().equals(targetDS))
					sameSourceAndTargetDenotationSet = false;
			}
		}
		return sameSourceAndTargetDenotationSet;
	}
	
	private static boolean sameTargetAttributeAsOneOtherProduction(IEdgeProduction edgeProduction, List<IEdgeProduction> edgeProductions) {
		if (edgeProductions.size() == 1)
			return true;
		boolean sameTargetAttributeAsOneOther = false;
		List<IEdgeProduction> others = new ArrayList<>(edgeProductions);
		others.remove(edgeProduction);
		for (IEdgeProduction other : others) {
			if (edgeProduction.getTarget().equals(other.getTarget()))
				sameTargetAttributeAsOneOther = true;
		}
		return sameTargetAttributeAsOneOther;
	}
	
	private static boolean sameValueAsOneOtherProduction(IEdgeProduction edgeProduction, List<IEdgeProduction> edgeProductions) {
		if (edgeProductions.size() == 1)
			return true;
		boolean sameValue = false;
		List<IEdgeProduction> others = new ArrayList<>(edgeProductions);
		others.remove(edgeProduction);
		for (IEdgeProduction other : others) {
			List<IConstruct> valuesOfOther = new ArrayList<>(other.getValues());
			if (valuesOfOther.removeAll(edgeProduction.getValues()))
				sameValue = true;
		}
		return sameValue;
	}
	
	@Before
	public void setUp() throws Exception {
		automatons = new TreeSet<>(ScoreThenCostTFComparator.INSTANCE);
		denotationSets = new DenotationSets(objects);
		List<IEdgeProduction> edgeProductions = new ProductionBuilder(denotationSets).getProductions();
		edgeProductions.stream().forEach(p -> {
			denotations.addVertex(p.getSource());
			denotations.addVertex(p.getTarget());
			denotations.addEdge(p.getSource(), p.getTarget(), p);
		});
		denotationSetsTreeSupplier = denotationSets.getDenotationSetsTreeSupplier();
		while (denotationSetsTreeSupplier.hasNext()) {
			Tree<IDenotationSet, IIsA> currTreeOfDenotationSets  = denotationSetsTreeSupplier.next();
			filtered_reduced_denotations = 
					TransitionFunctionSupplier.getDenotationGraphFilteredByTreeOfDenotationSets(
							currTreeOfDenotationSets, denotations);
			denotationTreeSupplier = new RestrictorOpt<>(filtered_reduced_denotations, true);
			while (denotationTreeSupplier.hasNext()) {
				denotationTree = denotationTreeSupplier.nextTransitiveReduction();
				IAutomaton automaton = 
						new Automaton(currTreeOfDenotationSets, denotationTree);
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
			for (ITransitionRule transitionRule : tF.getTransitions()) {
				if (transitionRule instanceof IOperator) {
					IOperator operator = (IOperator) transitionRule;
					for (IEdgeProduction edgeProduction : operator.operation()) {
						if (edgeProduction instanceof BlankEdgeProduction) {
						}
						else if (edgeProduction instanceof ISimpleEdgeProduction) {
							basicProds.add((ISimpleEdgeProduction) edgeProduction);
							basicProdsOperators.add(operator);
						}
						else {
							ICompositeEdgeProduction compoProd = (ICompositeEdgeProduction) edgeProduction;
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
					if (iProd.getSourceDenotationSet().equals(jProd.getSourceDenotationSet())
							&& iProd.getTargetDenotationSet().equals(jProd.getTargetDenotationSet())
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
			Map<IEdgeProduction, IOperator> prodToOpe = new HashMap<>();
			for (ITransitionRule transitionRule : tF.getTransitions()) {
				if (transitionRule instanceof IOperator) {
					IOperator operator = (IOperator) transitionRule;
					for (IEdgeProduction edgeProduction : operator.operation()) {
						prodToOpe.put(edgeProduction, operator);
					}
				}
			}
			List<IEdgeProduction> edgeProductions = new ArrayList<>(prodToOpe.keySet());
			for (int i = 0 ; i < edgeProductions.size() - 1 ; i++) {
				for (int j = i + 1 ; j < edgeProductions.size() ; j++) {
					IEdgeProduction iProd = edgeProductions.get(i);
					IEdgeProduction jProd = edgeProductions.get(j);
					if (iProd.getSourceDenotationSet().equals(jProd.getSourceDenotationSet())
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
			for (ITransitionRule transitionRule : tF.getTransitions()) {
				if (transitionRule instanceof IOperator) {
					IOperator operator = (IOperator) transitionRule;
					List<IEdgeProduction> edgeProductions = operator.operation();
					if (!sameSourceAndTargetCategoryForAll(edgeProductions))
						consistent = false;
					for (IEdgeProduction edgeProduction : edgeProductions) {
						if (!sameTargetAttributeAsOneOtherProduction(edgeProduction, edgeProductions) 
								&& !sameValueAsOneOtherProduction(edgeProduction, edgeProductions))
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
