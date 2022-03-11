package com.tregouet.occam.alg.scoring.costs.transitions.impl;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.TreeSet;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.alg.concepts_gen.transitions_gen.impl.ProductionSetBuilder;
import com.tregouet.occam.alg.preconcepts_gen.IPreconceptTreeSupplier;
import com.tregouet.occam.alg.scoring_dep.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring_dep.ScoringStrategy_dep;
import com.tregouet.occam.alg.scoring_dep.costs.transitions.ITransitionCoster;
import com.tregouet.occam.alg.scoring_dep.costs.transitions.TransitionCostingStrategy;
import com.tregouet.occam.alg.scoring_dep.costs.transitions.impl.TransitionCosterFactory;
import com.tregouet.occam.alg.transition_function_gen.impl.TransitionFunctionSupplier;
import com.tregouet.occam.data.automata.machines.IAutomaton;
import com.tregouet.occam.data.automata.machines.impl.Automaton;
import com.tregouet.occam.data.automata.machines.utils.ScoreThenCostTFComparator;
import com.tregouet.occam.data.languages.specific.IStronglyContextualized;
import com.tregouet.occam.data.preconcepts.IContextObject;
import com.tregouet.occam.data.preconcepts.IDenotation;
import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.preconcepts.IPreconcepts;
import com.tregouet.occam.data.preconcepts.impl.Preconcepts;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

@SuppressWarnings("unused")
public class EntropyReductionTest {
	
	private static final Path SHAPES1 = Paths.get(".", "src", "test", "java", "files", "shapes1bis.txt");
	private static List<IContextObject> shapes1Obj;
	private IPreconcepts preconcepts;
	private DirectedAcyclicGraph<IDenotation, IStronglyContextualized> denotations = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IPreconceptTreeSupplier preconceptTreeSupplier;
	private Tree<IPreconcept, IIsA> denotationTree;
	private DirectedAcyclicGraph<IDenotation, IStronglyContextualized> filtered_denotations;
	private IHierarchicalRestrictionFinder<IDenotation, IStronglyContextualized> denotationTreeSupplier;
	private Tree<IDenotation, IStronglyContextualized> treeOfDenotationSets;
	private TreeSet<IAutomaton> automatons = new TreeSet<>(ScoreThenCostTFComparator.INSTANCE);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes1Obj = GenericFileReader.getContextObjects(SHAPES1);
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy_dep.SCORING_STRATEGY_1);
	}

	@Before
	public void setUp() throws Exception {
		preconcepts = new Preconcepts(shapes1Obj);
		List<IStronglyContextualized> stronglyContextualizeds = new ProductionSetBuilder(preconcepts).getProductions();
		stronglyContextualizeds.stream().forEach(p -> {
			denotations.addVertex(p.getSource());
			denotations.addVertex(p.getTarget());
			denotations.addEdge(p.getSource(), p.getTarget(), p);
		});
		preconceptTreeSupplier = preconcepts.getConceptTreeSupplier();
		while (preconceptTreeSupplier.hasNext()) {
			denotationTree = preconceptTreeSupplier.next();
			filtered_denotations = 
					TransitionFunctionSupplier.getDenotationGraphFilteredByTreeOfDenotationSets(denotationTree, denotations);
			denotationTreeSupplier = new RestrictorOpt<>(filtered_denotations, true);
			while (denotationTreeSupplier.hasNext()) {
				treeOfDenotationSets = denotationTreeSupplier.next();
				IAutomaton automaton = 
						new Automaton(denotationTree, treeOfDenotationSets);
				automatons.add(automaton);
			}
		}
	}

	@Test
	public void whenTransitionCostRequestedThenReturned() throws IOException {
		boolean asExpected = true;
		int nbOfTests = 0;
		ITransitionCoster coster = 
				TransitionCosterFactory.INSTANCE.apply(TransitionCostingStrategy.ENTROPY_REDUCTION);
		for (IAutomaton automaton : automatons) {
			/*
			Visualizer.visualizeTransitionFunction(transitionFunction, "211229_entropyRedTest");
			*/
			coster.setCosterParameters(automaton);
			for (IConjunctiveTransition conjunctivetransition : automaton.getConjunctiveTransitions()) {
				Double transitionCost = null;
				for (ICostedTransition costedComponent : conjunctivetransition.getComponents()) {
					try {
						coster.input(costedComponent).setCost();
					}
					catch (Exception e) {
						asExpected = false;
					}
				}
				transitionCost = conjunctivetransition.getCostOfComponents();
				if (transitionCost == null)
					asExpected = false;
				nbOfTests++;
			}
		}
		if (nbOfTests == 0)
			asExpected = false;
		assertTrue(asExpected);
	}

}
