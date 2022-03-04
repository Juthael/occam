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

import com.tregouet.occam.alg.denotation_sets_gen.IConceptTreeSupplier;
import com.tregouet.occam.alg.scoring.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring.ScoringStrategy;
import com.tregouet.occam.alg.scoring.costs.transitions.ITransitionCoster;
import com.tregouet.occam.alg.scoring.costs.transitions.TransitionCostingStrategy;
import com.tregouet.occam.alg.transition_function_gen.impl.ProductionBuilder;
import com.tregouet.occam.alg.transition_function_gen.impl.TransitionFunctionSupplier;
import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.abstract_machines.automatons.impl.Automaton;
import com.tregouet.occam.data.abstract_machines.automatons.utils.ScoreThenCostTFComparator;
import com.tregouet.occam.data.denotations.IContextObject;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.denotations.IConcept;
import com.tregouet.occam.data.denotations.IConcepts;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.occam.data.denotations.impl.Concepts;
import com.tregouet.occam.data.languages.specific.IBasicProductionAsEdge;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

@SuppressWarnings("unused")
public class EntropyReductionTest {
	
	private static final Path SHAPES1 = Paths.get(".", "src", "test", "java", "files", "shapes1bis.txt");
	private static List<IContextObject> shapes1Obj;
	private IConcepts concepts;
	private DirectedAcyclicGraph<IDenotation, IBasicProductionAsEdge> denotations = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IConceptTreeSupplier conceptTreeSupplier;
	private Tree<IConcept, IIsA> denotationTree;
	private DirectedAcyclicGraph<IDenotation, IBasicProductionAsEdge> filtered_denotations;
	private IHierarchicalRestrictionFinder<IDenotation, IBasicProductionAsEdge> denotationTreeSupplier;
	private Tree<IDenotation, IBasicProductionAsEdge> treeOfDenotationSets;
	private TreeSet<IAutomaton> automatons = new TreeSet<>(ScoreThenCostTFComparator.INSTANCE);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes1Obj = GenericFileReader.getContextObjects(SHAPES1);
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy.SCORING_STRATEGY_1);
	}

	@Before
	public void setUp() throws Exception {
		concepts = new Concepts(shapes1Obj);
		List<IBasicProductionAsEdge> basicProductionAsEdges = new ProductionBuilder(concepts).getProductions();
		basicProductionAsEdges.stream().forEach(p -> {
			denotations.addVertex(p.getSource());
			denotations.addVertex(p.getTarget());
			denotations.addEdge(p.getSource(), p.getTarget(), p);
		});
		conceptTreeSupplier = concepts.getConceptTreeSupplier();
		while (conceptTreeSupplier.hasNext()) {
			denotationTree = conceptTreeSupplier.next();
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
