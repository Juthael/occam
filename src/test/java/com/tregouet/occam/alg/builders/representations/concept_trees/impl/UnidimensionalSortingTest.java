 package com.tregouet.occam.alg.builders.representations.concept_trees.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.InvertedTree;

public class UnidimensionalSortingTest {
	
	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	@SuppressWarnings("unused")
	private static final String nL = System.lineSeparator();
	private List<IContextObject> context;
	private IConceptLattice conceptLattice;
	Set<InvertedTree<IConcept, IIsA>> returnedTrees;

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
	public void whenTreesRequestedThenReturned() {
		UnidimensionalSorting treeBuilder = new UnidimensionalSorting();
		returnedTrees = treeBuilder.apply(conceptLattice);
		/*
		int idx = 0;
		System.out.println("Returned tree graphs are available at : " + nL);
		for (InvertedTree<IConcept, IIsA> tree : returnedTrees) {
			String path = 
					VisualizersAbstractFactory.INSTANCE.getConceptGraphViz().apply(
							tree, "UnidimensionalSortingTest" + Integer.toString(idx++));
			System.out.println(path);
		}
		*/
		assertTrue(returnedTrees != null && !returnedTrees.isEmpty());		
	}

}
