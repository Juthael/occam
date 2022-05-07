package com.tregouet.occam.alg.builders.representations.concept_trees.impl;

import static org.junit.Assert.*;

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
import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.InvertedTree;

public class IfLeafIsUniversalThenSortTest {

	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	public static int count = 0;
	@SuppressWarnings("unused")
	private static final String nL = System.lineSeparator();
	private List<IContextObject> context;
	private IConceptLattice conceptLattice;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OverallAbstractFactory.INSTANCE.apply(Occam.strategy);
	}

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);
		conceptLattice = GeneratorsAbstractFactory.INSTANCE.getConceptLatticeBuilder().apply(context);
	}

	@Test
	public void whenTreeExpansionRequestedThenProceeded() {
		Set<InvertedTree<IConcept, IIsA>> expansions = visualizeThenBuild(conceptLattice, null, "IfLeafIsUniversalTest_");
		assertTrue(!expansions.isEmpty());
	}
	
	private static Set<InvertedTree<IConcept, IIsA>> visualizeThenBuild(IConceptLattice conceptLattice, InvertedTree<IConcept, IIsA> tree, String name) {
		Set<InvertedTree<IConcept, IIsA>> recursivelyExpandedTrees = new HashSet<>();
		IfLeafIsUniversalThenSort sorter = new IfLeafIsUniversalThenSort();
		Set<InvertedTree<IConcept, IIsA>> expandedTrees = sorter.apply(conceptLattice, tree);
		recursivelyExpandedTrees.addAll(expandedTrees);
		for (InvertedTree<IConcept, IIsA> expandedTree : expandedTrees) {
			VisualizersAbstractFactory.INSTANCE.getConceptGraphViz().apply(expandedTree, name + Integer.toString(count));
			recursivelyExpandedTrees.addAll(visualizeThenBuild(conceptLattice, expandedTree, Integer.toString(count++)));
		}
		return recursivelyExpandedTrees;
	}

}
