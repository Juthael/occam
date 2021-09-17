package com.tregouet.occam.data.categories;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.graph.DefaultEdge;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.data.categories.impl.Categories;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.InTree;

public class ICatTreeSupplierTest {

	private static final Path shapes2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;	
	private ICategories categories;
	private ICatTreeSupplier treeSupplier;
	
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
	public void whenTunnelCategoriesRemovedThenNoMoreTunnelCategory() {
		boolean asExpected = true;
		int checkCount = 0;
		List<InTree<ICategory, DefaultEdge>> treesWithTunnelCategories = new ArrayList<>();
		List<InTree<ICategory, DefaultEdge>> treesAfterTunnelRemoval = new ArrayList<>();
		while (treeSupplier.hasNext()) {
			InTree<ICategory, DefaultEdge> current = treeSupplier.next();
			if (containsATunnelCategory(current))
				treesWithTunnelCategories.add(current);
		}
		for (InTree<ICategory, DefaultEdge> tree : treesWithTunnelCategories) {
			treesAfterTunnelRemoval.add(ICatTreeSupplier.removeTunnelCategories(tree));
		}
		for (InTree<ICategory, DefaultEdge> tree : treesAfterTunnelRemoval) {
			if (containsATunnelCategory(tree))
				asExpected = false;
			checkCount++;
		}
		assertTrue(asExpected && checkCount > 0);
	}
	
	@Test
	public void whenTunnelCategoriesRemovedThenTreeStillConnected() {
		boolean asExpected = true;
		int checkCount = 0;
		List<InTree<ICategory, DefaultEdge>> treesWithTunnelCategories = new ArrayList<>();
		List<InTree<ICategory, DefaultEdge>> treesAfterTunnelRemoval = new ArrayList<>();
		while (treeSupplier.hasNext()) {
			InTree<ICategory, DefaultEdge> current = treeSupplier.next();
			if (containsATunnelCategory(current))
				treesWithTunnelCategories.add(current);
		}
		for (InTree<ICategory, DefaultEdge> tree : treesWithTunnelCategories) {
			treesAfterTunnelRemoval.add(ICatTreeSupplier.removeTunnelCategories(tree));
		}
		for (InTree<ICategory, DefaultEdge> tree : treesAfterTunnelRemoval) {
			for (ICategory vertex : tree.vertexSet()) {
				if (tree.outDegreeOf(vertex) == 0 && vertex.type() != ICategory.ONTOLOGICAL_COMMITMENT)
					asExpected = false;
				if (tree.inDegreeOf(vertex) == 0 && vertex.type() != ICategory.OBJECT)
					asExpected = false;
			}
			checkCount++;
		}
		assertTrue(asExpected && checkCount > 0);
	}
	
	private boolean containsATunnelCategory(InTree<ICategory, DefaultEdge> tree) {
		for (ICategory category : tree.vertexSet()) {
			if (tree.inDegreeOf(category) == 1 && category.type() == ICategory.SUBSET_CAT)
				return true;
		}
		return false;
	}

}
