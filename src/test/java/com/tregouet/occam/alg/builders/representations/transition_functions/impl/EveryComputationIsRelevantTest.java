package com.tregouet.occam.alg.builders.representations.transition_functions.impl;

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
import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.classifications.impl.utils.MapConceptIDs2ExtentIDs;
import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.structures.representations.classifications.IClassification;
import com.tregouet.occam.data.structures.representations.classifications.concepts.ConceptType;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IContextObject;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IIsA;
import com.tregouet.occam.data.structures.representations.classifications.impl.Classification;
import com.tregouet.occam.data.structures.representations.descriptions.IDescription;
import com.tregouet.occam.data.structures.representations.productions.IContextualizedProduction;
import com.tregouet.occam.data.structures.representations.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.InvertedTree;

public class EveryComputationIsRelevantTest {

	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	private static final String nL = System.lineSeparator();
	private List<IContextObject> context;
	private IConceptLattice conceptLattice;
	private Set<InvertedTree<IConcept, IIsA>> trees;

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);
		conceptLattice = BuildersAbstractFactory.INSTANCE.getConceptLatticeBuilder().apply(context);
		trees = growTrees();
	}

	@Test
	public void whenTransitionFunctionRequestedThenReturned() {
		boolean asExpected = true;
		EveryComputationIsRelevant transFuncBldr;
		int nbOfChecks = 0;
		for (InvertedTree<IConcept, IIsA> tree : trees) {
			Map<Integer, List<Integer>> conceptID2ExtentIDs = MapConceptIDs2ExtentIDs.in(tree);
			Map<Integer, Integer> speciesID2GenusID = mapSpeciesID2GenusID(tree);
			boolean fullyDeveloped = isFullyDeveloped(tree);
			IClassification classification =
					new Classification(tree, conceptID2ExtentIDs, speciesID2GenusID,
							conceptLattice.getParticularID2Particular(), fullyDeveloped);
			Set<IContextualizedProduction> classProds =
					BuildersAbstractFactory.INSTANCE.getProductionSetBuilder().apply(classification);
			IDescription description = BuildersAbstractFactory.INSTANCE.getDescriptionBuilder().apply(classification, classProds);
			transFuncBldr = new EveryComputationIsRelevant();
			IRepresentationTransitionFunction transFunc = transFuncBldr.apply(classification, description);
			/*
			System.out.println(report(transFunc, tree, nbOfChecks));
			*/
			nbOfChecks++;
			if (transFunc == null)
				asExpected = false;
		}
		assertTrue(nbOfChecks > 0 && asExpected);
	}

	private Set<InvertedTree<IConcept, IIsA>> growTrees() {
		Set<InvertedTree<IConcept, IIsA>> expandedTrees = new HashSet<>();
		Set<InvertedTree<IConcept, IIsA>> expandedTreesFromLastIteration;
		expandedTreesFromLastIteration =
				new HashSet<>(BuildersAbstractFactory.INSTANCE.getConceptTreeGrower().apply(conceptLattice, null).keySet());
		do {
			expandedTrees.addAll(expandedTreesFromLastIteration);
			Set<InvertedTree<IConcept, IIsA>> expandable = new HashSet<>(expandedTreesFromLastIteration);
			expandedTreesFromLastIteration.clear();
			for (InvertedTree<IConcept, IIsA> tree : expandable) {
				expandedTreesFromLastIteration.addAll(new HashSet<>(
						BuildersAbstractFactory.INSTANCE.getConceptTreeGrower().apply(conceptLattice, tree).keySet()));
			}
		}
		while (!expandedTreesFromLastIteration.isEmpty());
		return expandedTrees;
	}

	@SuppressWarnings("unused")
	private String report(IRepresentationTransitionFunction transFunc, InvertedTree<IConcept, IIsA> tree, int idx) {
		StringBuilder sB = new StringBuilder();
		String treePath =
				VisualizersAbstractFactory.INSTANCE.getConceptGraphViz().apply(
						tree, "BuildExhaustivelyTest_tree" + Integer.toString(idx), true);
		String tfPath =
				VisualizersAbstractFactory.INSTANCE.getTransitionFunctionViz().apply(
						transFunc, "BuildExhaustivelyTest_TF" + Integer.toString(idx));
		sB.append("Concept tree n." + Integer.toString(idx) + " is available at : " + treePath + nL)
			.append("Transition function n." + Integer.toString(idx) + " is available at : " + tfPath);
		return sB.toString();
	}

	@BeforeClass
	public static void setUpBeforeClass() {
		Occam.initialize();
		OverallAbstractFactory.INSTANCE.apply(Occam.STRATEGY);
	}

	private static boolean isFullyDeveloped(InvertedTree<IConcept, IIsA> conceptTree) {
		for (IConcept concept : conceptTree.getLeaves()) {
			if (concept.type() != ConceptType.PARTICULAR)
				return false;
		}
		return true;
	}

	private static Map<Integer, Integer> mapSpeciesID2GenusID(InvertedTree<IConcept, IIsA> conceptTree) {
		Map<Integer, Integer> speciesID2GenusID = new HashMap<>();
		for (IIsA edge : conceptTree.edgeSet())
			speciesID2GenusID.put(conceptTree.getEdgeSource(edge).iD(), conceptTree.getEdgeTarget(edge).iD());
		return speciesID2GenusID;
	}

}
