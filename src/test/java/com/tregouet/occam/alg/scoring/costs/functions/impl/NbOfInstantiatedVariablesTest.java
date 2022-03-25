package com.tregouet.occam.alg.scoring.costs.functions.impl;

import static org.junit.Assert.*;

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
import com.tregouet.occam.alg.scoring_dep.costs.functions.FunctionCostingStrategy;
import com.tregouet.occam.alg.scoring_dep.costs.functions.IFunctionCoster;
import com.tregouet.occam.alg.scoring_dep.costs.functions.impl.FunctionCosterFactory;
import com.tregouet.occam.alg.transition_function_gen.impl.TransitionFunctionSupplier;
import com.tregouet.occam.data.languages.words.fact.IStronglyContextualized;
import com.tregouet.occam.data.logical_structures.automata.IAutomaton;
import com.tregouet.occam.data.logical_structures.automata.machines.deprec.Automaton_dep;
import com.tregouet.occam.data.logical_structures.automata.machines.utils.ScoreThenCostTFComparator;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IDenotation;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.concepts.impl.ConceptLattice;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

public class NbOfInstantiatedVariablesTest {
	
	private static final Path SHAPES = Paths.get(".", "src", "test", "java", "files", "shapes1bis.txt");
	private static List<IContextObject> objects;
	private IConceptLattice conceptLattice;
	private DirectedAcyclicGraph<IDenotation, IStronglyContextualized> denotations = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IConceptTreeBuilder conceptTreeBuilder;
	private DirectedAcyclicGraph<IDenotation, IStronglyContextualized> filtered_reduced_denotations;
	private IHierarchicalRestrictionFinder<IDenotation, IStronglyContextualized> denotationTreeSupplier;
	private Tree<IDenotation, IStronglyContextualized> denotationTree;
	private TreeSet<IAutomaton> automatons;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		objects = GenericFileReader.getContextObjects(SHAPES);	
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy_dep.SCORING_STRATEGY_1);
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
			Tree<IConcept, IIsA> currDSTree  = conceptTreeBuilder.next();
			filtered_reduced_denotations = 
					TransitionFunctionSupplier.getDenotationGraphFilteredByTreeOfDenotationSets(
							currDSTree, denotations);
			denotationTreeSupplier = new RestrictorOpt<>(filtered_reduced_denotations, true);
			while (denotationTreeSupplier.hasNext()) {
				denotationTree = denotationTreeSupplier.nextTransitiveReduction();
				IAutomaton automaton = 
						new Automaton_dep(currDSTree, denotationTree);
				automatons.add(automaton);
			}
		}
	}

	@Test
	public void whenTransitionFunctionCostRequestedThenReturned() {
		boolean costReturned = true;
		int nbOfTests = 0;
		IFunctionCoster coster = FunctionCosterFactory.INSTANCE.apply(FunctionCostingStrategy.NB_OF_INSTANTIATED_VARIABLES);
		for (IAutomaton tF : automatons) {
			Double tFCost = null;
			try {
				coster.input(tF).setCost();
				tFCost = tF.getCost();
			}
			catch (Exception e) {
				costReturned = false;
			}
			if (tFCost == null || tFCost == 0.0)
				costReturned = false;
			nbOfTests++;
		}
		if (nbOfTests == 0)
			costReturned = false;
		assertTrue(costReturned);
	}

}
