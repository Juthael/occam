package com.tregouet.occam.data.abstract_machines.functions.impl;

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
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.utils.ScoreThenCostTFComparator;
import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transitions.IBasicProduction;
import com.tregouet.occam.data.abstract_machines.transitions.ICompositeProduction;
import com.tregouet.occam.data.abstract_machines.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.abstract_machines.transitions.IOperator;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.abstract_machines.transitions.ITransition;
import com.tregouet.occam.data.abstract_machines.transitions.impl.BlankProduction;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IDenotationSets;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.occam.data.denotations.impl.DenotationSets;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.IContextObject;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

@SuppressWarnings("unused")
public class TransitionFunctionTest {

	private static final Path SHAPES = Paths.get(".", "src", "test", "java", "files", "shapes1bis.txt");
	private static List<IContextObject> objects;
	private IDenotationSets denotationSets;
	private DirectedAcyclicGraph<IDenotation, IProduction> denotations = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IDenotationSetsTreeSupplier denotationSetsTreeSupplier;
	private DirectedAcyclicGraph<IDenotation, IProduction> filtered_reduced_denotations;
	private IHierarchicalRestrictionFinder<IDenotation, IProduction> denotationTreeSupplier;
	private Tree<IDenotation, IProduction> denotationTree;
	private TreeSet<ITransitionFunction> transitionFunctions;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		objects = GenericFileReader.getContextObjects(SHAPES);	
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy.SCORING_STRATEGY_1);
	}

	private static double binaryLogarithm(double arg) {
		return Math.log10(arg)/Math.log10(2);
	}
	
	private static boolean sameSourceAndTargetCategoryForAll(List<IProduction> productions) {
		boolean sameSourceAndTargetDenotationSet = true;
		IDenotationSet sourceDS = null;
		IDenotationSet targetDS = null;
		for (int i = 0 ; i < productions.size() ; i++) {
			if (i == 0) {
				sourceDS = productions.get(i).getSourceDenotationSet();
				targetDS = productions.get(i).getTargetDenotationSet();
			}
			else {
				if (!productions.get(i).getSourceDenotationSet().equals(sourceDS)
						|| !productions.get(i).getTargetDenotationSet().equals(targetDS))
					sameSourceAndTargetDenotationSet = false;
			}
		}
		return sameSourceAndTargetDenotationSet;
	}
	
	private static boolean sameTargetAttributeAsOneOtherProduction(IProduction production, List<IProduction> productions) {
		if (productions.size() == 1)
			return true;
		boolean sameTargetAttributeAsOneOther = false;
		List<IProduction> others = new ArrayList<>(productions);
		others.remove(production);
		for (IProduction other : others) {
			if (production.getTarget().equals(other.getTarget()))
				sameTargetAttributeAsOneOther = true;
		}
		return sameTargetAttributeAsOneOther;
	}
	
	private static boolean sameValueAsOneOtherProduction(IProduction production, List<IProduction> productions) {
		if (productions.size() == 1)
			return true;
		boolean sameValue = false;
		List<IProduction> others = new ArrayList<>(productions);
		others.remove(production);
		for (IProduction other : others) {
			List<IConstruct> valuesOfOther = new ArrayList<>(other.getValues());
			if (valuesOfOther.removeAll(production.getValues()))
				sameValue = true;
		}
		return sameValue;
	}
	
	@Before
	public void setUp() throws Exception {
		transitionFunctions = new TreeSet<>(ScoreThenCostTFComparator.INSTANCE);
		denotationSets = new DenotationSets(objects);
		List<IProduction> productions = new ProductionBuilder(denotationSets).getProductions();
		productions.stream().forEach(p -> {
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
				ITransitionFunction transitionFunction = 
						new TransitionFunction(currTreeOfDenotationSets, denotationTree);
				transitionFunctions.add(transitionFunction);
			}
		}
	}
	
	@Test
	public void when2NonBlankProductionsHaveSameSourceAndTargetDenotSetsAndSameValueThenHandledBySameOperator() 
			throws IOException {
		boolean sameOperator = true;
		int checkCount = 0;
		for (ITransitionFunction tF : transitionFunctions) {
			/*
			System.out.println(tF.getDomainSpecificLanguage().toString());
			Visualizer.visualizeTransitionFunction(tF, "2108251050_tf", TransitionFunctionGraphType.FINITE_AUTOMATON);
			*/
			List<IBasicProduction> basicProds = new ArrayList<>();
			List<IOperator> basicProdsOperators = new ArrayList<>();
			for (ITransition transition : tF.getTransitions()) {
				if (transition instanceof IOperator) {
					IOperator operator = (IOperator) transition;
					for (IProduction production : operator.operation()) {
						if (production instanceof BlankProduction) {
						}
						else if (production instanceof IBasicProduction) {
							basicProds.add((IBasicProduction) production);
							basicProdsOperators.add(operator);
						}
						else {
							ICompositeProduction compoProd = (ICompositeProduction) production;
							for (IBasicProduction basicProd : compoProd.getComponents()) {
								basicProds.add(basicProd);
								basicProdsOperators.add(operator);
							}
						}
					}
				}
			}
			for (int i = 0 ; i < basicProds.size() - 1 ; i++) {
				for (int j = i + 1 ; j < basicProds.size() ; j++) {
					IBasicProduction iProd = basicProds.get(i);
					IBasicProduction jProd = basicProds.get(j);
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
		for (ITransitionFunction tF : transitionFunctions) {
			Map<IProduction, IOperator> prodToOpe = new HashMap<>();
			for (ITransition transition : tF.getTransitions()) {
				if (transition instanceof IOperator) {
					IOperator operator = (IOperator) transition;
					for (IProduction production : operator.operation()) {
						prodToOpe.put(production, operator);
					}
				}
			}
			List<IProduction> productions = new ArrayList<>(prodToOpe.keySet());
			for (int i = 0 ; i < productions.size() - 1 ; i++) {
				for (int j = i + 1 ; j < productions.size() ; j++) {
					IProduction iProd = productions.get(i);
					IProduction jProd = productions.get(j);
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
		for (ITransitionFunction tF : transitionFunctions) {
			for (ITransition transition : tF.getTransitions()) {
				if (transition instanceof IOperator) {
					IOperator operator = (IOperator) transition;
					List<IProduction> productions = operator.operation();
					if (!sameSourceAndTargetCategoryForAll(productions))
						consistent = false;
					for (IProduction production : productions) {
						if (!sameTargetAttributeAsOneOtherProduction(production, productions) 
								&& !sameValueAsOneOtherProduction(production, productions))
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
		for (ITransitionFunction tF : transitionFunctions) {
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
