package com.tregouet.occam.alg.builders.representations.descriptions.impl;

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
import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.data.structures.representations.classifications.IClassification;
import com.tregouet.occam.data.structures.representations.classifications.concepts.ConceptType;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IContextObject;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IIsA;
import com.tregouet.occam.data.structures.representations.classifications.impl.Classification;
import com.tregouet.occam.data.structures.representations.descriptions.IDescription;
import com.tregouet.occam.data.structures.representations.productions.IContextualizedProduction;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.InvertedTree;

@SuppressWarnings("unused")
public class BuildTreeThenCalculateMetricsTest {

	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	private static final String nL = System.lineSeparator();
	private List<IContextObject> context;
	private IConceptLattice conceptLattice;
	private Set<InvertedTree<IConcept, IIsA>> trees;
	private Map<Set<IContextualizedProduction>, IClassification> classProd2Classification =	new HashMap<>();

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);
		conceptLattice = BuildersAbstractFactory.INSTANCE.getConceptLatticeBuilder().apply(context);
		trees = growTrees();
		RepresentationTransFuncBuilder transFuncBldr;
		for (InvertedTree<IConcept, IIsA> tree : trees) {
			transFuncBldr = BuildersAbstractFactory.INSTANCE.getRepresentationTransFuncBuilder();
			Map<Integer, List<Integer>> conceptID2ExtentIDs = MapConceptIDs2ExtentIDs.in(tree);
			Map<Integer, Integer> speciesID2GenusID = mapSpeciesID2GenusID(tree);
			boolean fullyDeveloped = isFullyDeveloped(tree);
			IClassification classification = new Classification(tree, conceptID2ExtentIDs, speciesID2GenusID,
					conceptLattice.getParticularID2Particular(), fullyDeveloped);
			Set<IContextualizedProduction> classProds = BuildersAbstractFactory.INSTANCE.getProductionSetBuilder().apply(classification);
			classProd2Classification.put(classProds, classification);
		}
	}

	@Test
	public void whenDescriptionsRequestedThenReturned() {
		Set<IDescription> descriptions = new HashSet<>();
		int checkIdx = 0;
		for (Set<IContextualizedProduction> classProd : classProd2Classification.keySet()) {
			IClassification classification = classProd2Classification.get(classProd);
			IDescription description =
					BuildTreeThenCalculateMetrics.INSTANCE.apply(classification, classProd);
			/*
			String descriptionPath =
					VisualizersAbstractFactory.INSTANCE.getDescriptionViz().apply(
							description, "BuildTreeThenCalcTest_" + Integer.toString(checkIdx));
			System.out.println("Description graph n." + Integer.toString(checkIdx) + " available at" + descriptionPath);
			*/
			descriptions.add(description);
			checkIdx++;
		}
		assertTrue(descriptions.size() == classProd2Classification.size());
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
