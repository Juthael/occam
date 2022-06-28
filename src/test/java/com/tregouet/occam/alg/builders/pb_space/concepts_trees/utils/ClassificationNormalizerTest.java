package com.tregouet.occam.alg.builders.pb_space.concepts_trees.utils;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.utils.ClassificationNormalizer;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.InvertedTree;

public class ClassificationNormalizerTest {

	private static final Path TABLETOP1B = Paths.get(".", "src", "test", "java", "files", "tabletop1b.txt");
	private List<IContextObject> context;
	private Set<Integer> particularIDs = new HashSet<>();
	private IConceptLattice conceptLattice;
	private Set<InvertedTree<IConcept, IIsA>> conceptTrees;

	@BeforeClass
	public static void setUpBeforeClass() {
		OverallAbstractFactory.INSTANCE.apply(Occam.strategy);
		Occam.initialize();
	}

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(TABLETOP1B);
		for (IContextObject obj : context)
			particularIDs.add(obj.iD());
		conceptLattice = BuildersAbstractFactory.INSTANCE.getConceptLatticeBuilder().apply(context);
		/*
		VisualizersAbstractFactory.INSTANCE.getConceptGraphViz()
			.apply(conceptLattice.getOntologicalUpperSemilattice(), "FirstBuildTransitionFunctionTest_lattice");
		*/
		conceptTrees = growTrees();
	}

	@Test
	public void whenNormalizedClassificationsRequiredThenReturned() {
		Set<IClassification> normalizedTrees = new HashSet<>();
		/*
		int idx = 0;
		*/
		for (InvertedTree<IConcept, IIsA> conceptTree : conceptTrees) {
			IClassification classification =
					BuildersAbstractFactory.INSTANCE.getClassificationBuilder().apply(conceptTree, particularIDs);
			/*
			VisualizersAbstractFactory.INSTANCE.getConceptGraphViz().apply(conceptTree, "raw" + Integer.toString(idx));
			*/
			IClassification normalized = ClassificationNormalizer.normalize(classification);
			if (normalized != null)
				normalizedTrees.add(normalized);
			/*
			VisualizersAbstractFactory.INSTANCE.getConceptGraphViz().apply(normalized.asGraph(), "norm" + Integer.toString(idx++));
			*/
		}
		assertTrue(normalizedTrees.size() == conceptTrees.size());
	}

	private Set<InvertedTree<IConcept, IIsA>> growTrees() {
		Set<InvertedTree<IConcept, IIsA>> expandedTrees = new HashSet<>();
		Set<InvertedTree<IConcept, IIsA>> expandedTreesFromLastIteration;
		expandedTreesFromLastIteration = BuildersAbstractFactory.INSTANCE.getConceptTreeGrower().apply(conceptLattice, null);
		do {
			expandedTrees.addAll(expandedTreesFromLastIteration);
			Set<InvertedTree<IConcept, IIsA>> expandable = new HashSet<>(expandedTreesFromLastIteration);
			expandedTreesFromLastIteration.clear();
			for (InvertedTree<IConcept, IIsA> tree : expandable) {
				expandedTreesFromLastIteration.addAll(
						BuildersAbstractFactory.INSTANCE.getConceptTreeGrower().apply(conceptLattice, tree));
			}
		}
		while (!expandedTreesFromLastIteration.isEmpty());
		return expandedTrees;
	}

}
