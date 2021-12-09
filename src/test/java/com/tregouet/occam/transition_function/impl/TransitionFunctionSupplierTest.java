package com.tregouet.occam.transition_function.impl;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.concepts.impl.Concepts;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.transitions.IProduction;
import com.tregouet.occam.data.transitions.impl.ProductionBuilder;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.io.output.utils.Visualizer;
import com.tregouet.occam.transition_function.IClassificationTreeSupplier;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.StructureInspector;

@SuppressWarnings("unused")
public class TransitionFunctionSupplierTest {

	private static final Path SHAPES2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;	
	private IConcepts concepts;
	private IClassificationTreeSupplier classificationTreeSupplier;
	private DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs = 
			new DirectedAcyclicGraph<>(null, null, false);
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(SHAPES2);

	}

	@Before
	public void setUp() throws Exception {
		concepts = new Concepts(shapes2Obj);
		List<IProduction> productions = new ProductionBuilder(concepts).getProductions();
		productions.stream().forEach(p -> {
			constructs.addVertex(p.getSource());
			constructs.addVertex(p.getTarget());
			constructs.addEdge(p.getSource(), p.getTarget(), p);
		});
		classificationTreeSupplier = concepts.getCatTreeSupplier();
	}

	@Test
	public void whenConstructGraphIsFilteredByCategoryTreeThenSetOfProductionsSourcesOrTargetsIsTreeOfCategories() 
			throws IOException {
		boolean expectedSetOfCategories = true;
		while (classificationTreeSupplier.hasNext()) {
			Tree<IConcept, IsA> catTree = classificationTreeSupplier.nextOntologicalCommitment();
			/*
			Visualizer.visualizeCategoryGraph(catTree, "2111051022_catTree");
			*/
			Set<IConcept> expectedCats = catTree.vertexSet();
			Set<IConcept> returnedCats = new HashSet<>();
			DirectedAcyclicGraph<IIntentAttribute, IProduction> filteredConstructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByCategoryTree(catTree, constructs);
			for (IProduction production : filteredConstructs.edgeSet()) {
				returnedCats.add(production.getSourceCategory());
				returnedCats.add(production.getTargetCategory());
			}
			if (!expectedCats.equals(returnedCats)) {
				expectedSetOfCategories = false;
				/*
				Visualizer.visualizeAttributeGraph(filteredConstructs, "2111051022_filteredConstructs");
				*/
			}
				
		}
		assertTrue(expectedSetOfCategories);
	}
	
	@Test
	public void whenConstructGraphIsFilteredByCategoryTreeThenSetOfContainerCategoriesIsTreeOfCategories() {
		boolean expectedSetOfCategories = true;
		while (classificationTreeSupplier.hasNext()) {
			Tree<IConcept, IsA> catTree = classificationTreeSupplier.nextOntologicalCommitment();
			Set<IConcept> expectedCats = catTree.vertexSet();
			Set<IConcept> returnedCats = new HashSet<>();
			DirectedAcyclicGraph<IIntentAttribute, IProduction> filteredConstructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByCategoryTree(catTree, constructs);
			for (IIntentAttribute intentAtt : filteredConstructs.vertexSet()) {
				returnedCats.add(intentAtt.getConcept());
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
			Tree<IConcept, IsA> catTree = classificationTreeSupplier.nextOntologicalCommitment();
			DirectedAcyclicGraph<IIntentAttribute, IProduction> filteredConstructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByCategoryTree(catTree, constructs);
			for (IProduction production : filteredConstructs.edgeSet()) {
				checkCount++;
				IConcept sourceCat = production.getSourceCategory();
				IConcept targetCat = production.getTargetCategory();
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
			Tree<IConcept, IsA> catTree = classificationTreeSupplier.nextOntologicalCommitment();
			/*
			Visualizer.visualizeCategoryGraph(catTree, "2108141517_cats");
			*/
			DirectedAcyclicGraph<IIntentAttribute, IProduction> filteredConstructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByCategoryTree(catTree, constructs);
			/*
			Visualizer.visualizeAttributeGraph(filteredConstructs, "2108141517_atts");
			System.out.println(checkCount);
			*/
			if (!StructureInspector.isARootedInvertedDirectedAcyclicGraph(filteredConstructs)) {
				filteredGraphsAreRootedInvertedDAGs = false;
			}
			checkCount++;
		}
		assertTrue(filteredGraphsAreRootedInvertedDAGs && checkCount > 0);
	}
	
}
