package com.tregouet.occam.data.categories.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.jgrapht.graph.DefaultEdge;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IClassificationTreeSupplier;
import com.tregouet.occam.data.categories.impl.Categories;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.InTree;
import com.tregouet.tree_finder.error.InvalidTreeException;

public class ClassificationTreeSupplierTest {

	private static final Path shapes2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;	
	private ICategories categories;
	private IClassificationTreeSupplier treeSupplier;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(shapes2);
	}

	@Before
	public void setUp() throws Exception {
		categories = new Categories(shapes2Obj);
		treeSupplier = categories.getCatTreeSupplier();
	}

	@Test
	public void whenTreeSuppliedThenReallyIsATree() throws IOException {
		int nbOfChecks = 0;
		while (treeSupplier.hasNext()) {
			InTree<ICategory, DefaultEdge> nextTree = treeSupplier.next();
			/*
			Visualizer.visualizeCategoryGraph(nextTree, "2109231614_classification" + Integer.toString(nbOfChecks));
			*/
			try {
				nextTree.validate();
				nbOfChecks++;
			}
			catch (InvalidTreeException e) {
				fail();
			}
		}
		assertTrue(nbOfChecks > 0);
	}

}
