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

import com.tregouet.occam.alg.builders.concepts.trees.IConceptTreeBuilder;
import com.tregouet.occam.alg.builders.representations.productions.from_concepts.impl.IfIsAThenBuildProductions;
import com.tregouet.occam.alg.scoring_dep.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring_dep.ScoringStrategy_dep;
import com.tregouet.occam.alg.scoring_dep.costs.transitions.ITransitionCoster;
import com.tregouet.occam.alg.scoring_dep.costs.transitions.TransitionCostingStrategy;
import com.tregouet.occam.alg.scoring_dep.costs.transitions.impl.TransitionCosterFactory;
import com.tregouet.occam.alg.transition_function_gen.impl.TransitionFunctionSupplier;
import com.tregouet.occam.data.automata.IAutomaton;
import com.tregouet.occam.data.automata.machines.deprec.Automaton_dep;
import com.tregouet.occam.data.automata.machines.utils.ScoreThenCostTFComparator;
import com.tregouet.occam.data.concepts.IContextObject;
import com.tregouet.occam.data.concepts.IDenotation;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConceptLattice;
import com.tregouet.occam.data.concepts.impl.ConceptLattice;
import com.tregouet.occam.data.languages.specific.IStronglyContextualized;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

@SuppressWarnings("unused")
public class EntropyReductionTest {
	
	private static final Path SHAPES1 = Paths.get(".", "src", "test", "java", "files", "shapes1bis.txt");
	private static List<IContextObject> shapes1Obj;
	private IConceptLattice conceptLattice;
	private DirectedAcyclicGraph<IDenotation, IStronglyContextualized> denotations = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IConceptTreeBuilder conceptTreeBuilder;
	private Tree<IConcept, IIsA> denotationTree;
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
		conceptLattice = new ConceptLattice(shapes1Obj);
		List<IStronglyContextualized> stronglyContextualizeds = new IfIsAThenBuildProductions(conceptLattice).getProductions();
		stronglyContextualizeds.stream().forEach(p -> {
			denotations.addVertex(p.getSource());
			denotations.addVertex(p.getTarget());
			denotations.addEdge(p.getSource(), p.getTarget(), p);
		});
		conceptTreeBuilder = conceptLattice.getConceptTreeSupplier();
		while (conceptTreeBuilder.hasNext()) {
			denotationTree = conceptTreeBuilder.next();
			filtered_denotations = 
					TransitionFunctionSupplier.getDenotationGraphFilteredByTreeOfDenotationSets(denotationTree, denotations);
			denotationTreeSupplier = new RestrictorOpt<>(filtered_denotations, true);
			while (denotationTreeSupplier.hasNext()) {
				treeOfDenotationSets = denotationTreeSupplier.next();
				IAutomaton automaton = 
						new Automaton_dep(denotationTree, treeOfDenotationSets);
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
