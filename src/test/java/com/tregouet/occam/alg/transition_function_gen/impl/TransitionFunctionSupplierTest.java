package com.tregouet.occam.alg.transition_function_gen.impl;

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

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.conceptual_structure_gen.IConceptTreeSupplier;
import com.tregouet.occam.alg.scoring.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring.ScoringStrategy;
import com.tregouet.occam.alg.transition_function_gen.impl.ProductionBuilder;
import com.tregouet.occam.alg.transition_function_gen.impl.TransitionFunctionSupplier;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.concepts.IComplementaryConcept;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IIntentConstruct;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.occam.data.concepts.impl.Concepts;
import com.tregouet.occam.data.languages.generic.IContextObject;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.io.output.utils.Visualizer;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.StructureInspector;

@SuppressWarnings("unused")
public class TransitionFunctionSupplierTest {

	private static final Path SHAPES2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;	
	private IConcepts concepts;
	private IConceptTreeSupplier conceptTreeSupplier;
	private DirectedAcyclicGraph<IIntentConstruct, IProduction> constructs = 
			new DirectedAcyclicGraph<>(null, null, false);
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(SHAPES2);
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy.SCORING_STRATEGY_1);
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
		conceptTreeSupplier = concepts.getClassificationSupplier();
	}

	@Test
	public void whenConstructGraphFilteredByConceptTreeThenSetOfProdSourcesOrTargetsIsConceptTreeMinusFramingConcepts() 
			throws IOException {
		boolean expectedSetOfCategories = true;
		while (conceptTreeSupplier.hasNext()) {
			Tree<IConcept, IIsA> catTree = conceptTreeSupplier.next();
			Set<IConcept> expectedCats = catTree.vertexSet();
			Set<IConcept> returnedCats = new HashSet<>();
			DirectedAcyclicGraph<IIntentConstruct, IProduction> filteredConstructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByConceptTree(catTree, constructs);
			for (IProduction production : filteredConstructs.edgeSet()) {
				returnedCats.add(production.getSourceCategory());
				returnedCats.add(production.getTargetConcept());
			}
			if (!expectedCats.equals(returnedCats)) {
				Set<IConcept> conceptsNotFound = Sets.difference(expectedCats, returnedCats);
				for (IConcept notFound : conceptsNotFound) {
					if (!notFound.isComplementary() && 
							((IComplementaryConcept) notFound).getEmbeddedConcept() != null)
						expectedSetOfCategories = false;
				}
			}
				
		}
		assertTrue(expectedSetOfCategories);
	}
	
	@Test
	public void whenConstructGraphIsFilteredByConceptTreeThenSetOfContainingConceptsIsConceptTreeMinusFramingConcepts() {
		boolean expectedSetOfCategories = true;
		while (conceptTreeSupplier.hasNext()) {
			Tree<IConcept, IIsA> catTree = conceptTreeSupplier.next();
			Set<IConcept> expectedCats = catTree.vertexSet();
			Set<IConcept> returnedCats = new HashSet<>();
			DirectedAcyclicGraph<IIntentConstruct, IProduction> filteredConstructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByConceptTree(catTree, constructs);
			for (IIntentConstruct intentAtt : filteredConstructs.vertexSet()) {
				returnedCats.add(intentAtt.getConcept());
			}
			if (!expectedCats.equals(returnedCats)) {
				Set<IConcept> conceptsNotFound = Sets.difference(expectedCats, returnedCats);
				for (IConcept notFound : conceptsNotFound) {
					if (!notFound.isComplementary() 
							&& ((IComplementaryConcept) notFound).getEmbeddedConcept() != null)
						expectedSetOfCategories = false;
				}
			}
		}
		assertTrue(expectedSetOfCategories);
	}	
	
	@Test
	public void whenConstructGraphIsFilteredByCategoryTreeThenProductionsSourceAndTargetCatsAreRelatedInCatTree() {
		boolean sourceAndTargetCatsAreRelated = true;
		int checkCount = 0;
		while (conceptTreeSupplier.hasNext()) {
			Tree<IConcept, IIsA> catTree = conceptTreeSupplier.next();
			DirectedAcyclicGraph<IIntentConstruct, IProduction> filteredConstructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByConceptTree(catTree, constructs);
			for (IProduction production : filteredConstructs.edgeSet()) {
				checkCount++;
				IConcept sourceCat = production.getSourceCategory();
				IConcept targetCat = production.getTargetConcept();
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
		while (conceptTreeSupplier.hasNext() && filteredGraphsAreRootedInvertedDAGs) {
			Tree<IConcept, IIsA> catTree = conceptTreeSupplier.next();
			/*
			Visualizer.visualizeCategoryGraph(catTree, "2108141517_cats");
			*/
			DirectedAcyclicGraph<IIntentConstruct, IProduction> filteredConstructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByConceptTree(catTree, constructs);
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
