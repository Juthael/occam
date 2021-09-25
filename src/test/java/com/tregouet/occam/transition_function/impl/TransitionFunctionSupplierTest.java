package com.tregouet.occam.transition_function.impl;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.data.categories.IClassificationTreeSupplier;
import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.categories.impl.Categories;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.data.operators.impl.ProductionBuilder;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.io.output.utils.Visualizer;
import com.tregouet.tree_finder.ITreeFinder;
import com.tregouet.tree_finder.data.InTree;
import com.tregouet.tree_finder.error.InvalidSemiLatticeException;
import com.tregouet.tree_finder.impl.TreeFinder;

public class TransitionFunctionSupplierTest {

	private static final Path shapes2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;	
	private static ICategories categories;
	private static IClassificationTreeSupplier classificationTreeSupplier;
	private static final DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs = 
			new DirectedAcyclicGraph<>(null, null, false);
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(shapes2);
		categories = new Categories(shapes2Obj);
		List<IProduction> productions = new ProductionBuilder(categories).getProductions();
		productions.stream().forEach(p -> {
			constructs.addVertex(p.getSource());
			constructs.addVertex(p.getTarget());
			constructs.addEdge(p.getSource(), p.getTarget(), p);
		});
	}

	@Before
	public void setUp() throws Exception {
		classificationTreeSupplier = categories.getCatTreeSupplier();
	}

	@Test
	public void whenConstructGraphIsFilteredByCategoryTreeThenSetOfProductionsSourcesOrTargetsIsTreeOfCategories() {
		boolean expectedSetOfCategories = true;
		while (classificationTreeSupplier.hasNext()) {
			InTree<ICategory, DefaultEdge> catTree = classificationTreeSupplier.next();
			Set<ICategory> expectedCats = catTree.vertexSet();
			Set<ICategory> returnedCats = new HashSet<>();
			DirectedAcyclicGraph<IIntentAttribute, IProduction> filteredConstructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByCategoryTreeThenReduced(catTree, constructs);
			for (IProduction production : filteredConstructs.edgeSet()) {
				returnedCats.add(production.getSourceCategory());
				returnedCats.add(production.getTargetCategory());
			}
			if (!expectedCats.equals(returnedCats))
				expectedSetOfCategories = false;
		}
		assertTrue(expectedSetOfCategories);
	}
	
	@Test
	public void whenConstructGraphIsFilteredByCategoryTreeThenSetOfContainerCategoriesIsTreeOfCategories() {
		boolean expectedSetOfCategories = true;
		while (classificationTreeSupplier.hasNext()) {
			InTree<ICategory, DefaultEdge> catTree = classificationTreeSupplier.next();
			Set<ICategory> expectedCats = catTree.vertexSet();
			Set<ICategory> returnedCats = new HashSet<>();
			DirectedAcyclicGraph<IIntentAttribute, IProduction> filteredConstructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByCategoryTreeThenReduced(catTree, constructs);
			for (IIntentAttribute intentAtt : filteredConstructs.vertexSet()) {
				returnedCats.add(intentAtt.getCategory());
			}
			if (!expectedCats.equals(returnedCats))
				expectedSetOfCategories = false;
		}
		assertTrue(expectedSetOfCategories);
	}	
	
	@Test
	public void whenConstructGraphIsFilteredByCategoryTreeThenProductionsSourceAndTargetCatsAreRelatedInCatTree() {
		boolean sourceAndTargetCatsAreRelated = true;
		int checkCount = 0;
		while (classificationTreeSupplier.hasNext()) {
			InTree<ICategory, DefaultEdge> catTree = classificationTreeSupplier.next();
			DirectedAcyclicGraph<IIntentAttribute, IProduction> filteredConstructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByCategoryTreeThenReduced(catTree, constructs);
			for (IProduction production : filteredConstructs.edgeSet()) {
				checkCount++;
				ICategory sourceCat = production.getSourceCategory();
				ICategory targetCat = production.getTargetCategory();
				if (!catTree.getDescendants(sourceCat).contains(targetCat))
					sourceAndTargetCatsAreRelated = false;
			}
		}
		assertTrue(sourceAndTargetCatsAreRelated && checkCount > 0);
	}
	
	@Test
	public void whenConstructGraphFilteredByCategoryTreeThenOrderedSetOfConstructsIsARootedInvertedDAG() throws IOException {
		boolean filteredGraphsAreRootedInvertedDAGs = true;
		int checkCount = 0;
		while (classificationTreeSupplier.hasNext() && filteredGraphsAreRootedInvertedDAGs) {
			InTree<ICategory, DefaultEdge> catTree = classificationTreeSupplier.next();
			DirectedAcyclicGraph<IIntentAttribute, IProduction> filteredConstructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByCategoryTreeThenReduced(catTree, constructs);
			try {
				//safe constructor
				@SuppressWarnings("unused")
				ITreeFinder<IIntentAttribute, IProduction> safeTreeFinder = new TreeFinder<>(filteredConstructs, true);
				checkCount++;
			}
			catch (InvalidSemiLatticeException e) {
				filteredGraphsAreRootedInvertedDAGs = false;
				Visualizer.visualizeAttributeGraph(filteredConstructs, "2108141517_atts");
				Visualizer.visualizeCategoryGraph(catTree, "2108141517_cats");
			}
		}
		assertTrue(filteredGraphsAreRootedInvertedDAGs && checkCount > 0);
	}
	
}
