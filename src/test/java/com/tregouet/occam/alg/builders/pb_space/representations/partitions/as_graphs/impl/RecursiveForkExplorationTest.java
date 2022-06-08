package com.tregouet.occam.alg.builders.pb_space.representations.partitions.as_graphs.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.OverallStrategy;
import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.classification_productions.ClassificationProductionSetBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.partitions.graphs.impl.RecursiveForkExploration;
import com.tregouet.occam.alg.builders.pb_space.utils.MapConceptIDs2ExtentIDs;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.classifications.impl.Classification;
import com.tregouet.occam.data.problem_space.states.descriptions.IDescription;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.productions.IClassificationProductions;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.InvertedTree;
import com.tregouet.tree_finder.data.Tree;

public class RecursiveForkExplorationTest {
	
	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	private List<IContextObject> context;
	private Set<Integer> extentIDs = new HashSet<>();
	private IConceptLattice conceptLattice;	
	private Set<IContextualizedProduction> productions;
	private Set<InvertedTree<IConcept, IIsA>> trees;
	private Map<IClassificationProductions, IClassification> classProd2Classification =	new HashMap<>();
	private Set<IDescription> descriptions = new HashSet<>();	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OverallAbstractFactory.INSTANCE.apply(Occam.strategy);
	}

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);
		for (IContextObject obj : context)
			extentIDs.add(obj.iD());
		conceptLattice = BuildersAbstractFactory.INSTANCE.getConceptLatticeBuilder().apply(context);
		if (Occam.strategy == OverallStrategy.OVERALL_STRATEGY_1 || Occam.strategy == OverallStrategy.OVERALL_STRATEGY_2)
			productions = BuildersAbstractFactory.INSTANCE.getProdBuilderFromConceptLattice().apply(conceptLattice);
		growTrees();
		ClassificationProductionSetBuilder bldr;
		for (InvertedTree<IConcept, IIsA> tree : trees) {
			Map<Integer, List<Integer>> conceptID2ExtentIDs = MapConceptIDs2ExtentIDs.in(tree);
			Map<Integer, Integer> speciesID2GenusID = mapSpeciesID2GenusID(tree);
			IClassification classification = new Classification(tree, conceptID2ExtentIDs, speciesID2GenusID, extentIDs);
			bldr = BuildersAbstractFactory.INSTANCE.getClassificationProductionSetBuilder();
			IClassificationProductions classProds = bldr.setUp(productions).apply(classification);
			classProd2Classification.put(classProds, classification);
		}
		for (IClassificationProductions classProd : classProd2Classification.keySet()) {
			descriptions.add(BuildersAbstractFactory.INSTANCE
					.getDescriptionBuilder().apply(classProd2Classification.get(classProd), classProd));
		}
	}

	@Test
	public void whenPartitionGraphsRequestedThenValidTreesReturned() {
		boolean asExpected = true;
		int nbOfChecks = 0;
		for (IDescription description : descriptions) {
			Set<Tree<Integer, ADifferentiae>> partitionGraphs = RecursiveForkExploration.INSTANCE.apply(description.asGraph());
			if (partitionGraphs == null || partitionGraphs.isEmpty())
				asExpected = false;
			for (Tree<Integer, ADifferentiae> graph : partitionGraphs) {
				try {
					graph.validate();
				}
				catch (Exception e) {
					asExpected = false;
				}
			}
			nbOfChecks++;
		}
		assertTrue(nbOfChecks > 0 && asExpected);
	}
	
	private void growTrees() {
		trees = BuildersAbstractFactory.INSTANCE.getConceptTreeGrower().apply(conceptLattice, null);
		boolean newTreesBuilt = true;
		Set<InvertedTree<IConcept, IIsA>> previouslyFoundTrees = new HashSet<>();
		previouslyFoundTrees.addAll(trees);
		while (newTreesBuilt) {
			Set<InvertedTree<IConcept, IIsA>> foundTrees = new HashSet<>();
			for (InvertedTree<IConcept, IIsA> tree : previouslyFoundTrees)
				foundTrees.addAll(BuildersAbstractFactory.INSTANCE.getConceptTreeGrower().apply(conceptLattice, tree));
			newTreesBuilt = !(foundTrees.isEmpty());
			trees.addAll(foundTrees);
			previouslyFoundTrees = foundTrees;
		}
	}	
	
	private static Map<Integer, Integer> mapSpeciesID2GenusID(InvertedTree<IConcept, IIsA> conceptTree) {
		Map<Integer, Integer> speciesID2GenusID = new HashMap<>();
		for (IIsA edge : conceptTree.edgeSet())
			speciesID2GenusID.put(conceptTree.getEdgeSource(edge).iD(), conceptTree.getEdgeTarget(edge).iD());
		return speciesID2GenusID;
	}	

}
