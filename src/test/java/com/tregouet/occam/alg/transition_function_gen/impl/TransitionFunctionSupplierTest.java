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
import com.tregouet.occam.alg.concepts_gen.IConceptTreeSupplier;
import com.tregouet.occam.alg.scoring.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring.ScoringStrategy;
import com.tregouet.occam.alg.transition_function_gen.impl.ProductionBuilder;
import com.tregouet.occam.alg.transition_function_gen.impl.TransitionFunctionSupplier;
import com.tregouet.occam.data.concepts.IComplementaryConcept;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IContextObject;
import com.tregouet.occam.data.concepts.IDenotation;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.occam.data.concepts.impl.Concepts;
import com.tregouet.occam.data.languages.specific.IProductionAsEdge;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.StructureInspector;

@SuppressWarnings("unused")
public class TransitionFunctionSupplierTest {

	private static final Path SHAPES2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;	
	private IConcepts concepts;
	private IConceptTreeSupplier conceptTreeSupplier;
	private DirectedAcyclicGraph<IDenotation, IProductionAsEdge> denotations = 
			new DirectedAcyclicGraph<>(null, null, false);
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(SHAPES2);
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy.SCORING_STRATEGY_1);
	}

	@Before
	public void setUp() throws Exception {
		concepts = new Concepts(shapes2Obj);
		List<IProductionAsEdge> productionAsEdges = new ProductionBuilder(concepts).getProductions();
		productionAsEdges.stream().forEach(p -> {
			denotations.addVertex(p.getSource());
			denotations.addVertex(p.getTarget());
			denotations.addEdge(p.getSource(), p.getTarget(), p);
		});
		conceptTreeSupplier = concepts.getConceptTreeSupplier();
	}

	@Test
	public void whenDenotationGraphFilteredByTreeOfDenotSetsThenOrderedSetOfDenotationsIsARootedInvertedDAG() throws IOException {
		boolean filteredGraphsAreRootedInvertedDAGs = true;
		int checkCount = 0;
		while (conceptTreeSupplier.hasNext() && filteredGraphsAreRootedInvertedDAGs) {
			Tree<IConcept, IIsA> catTree = conceptTreeSupplier.next();
			/*
			Visualizer.visualizeCategoryGraph(catTree, "2108141517_cats");
			*/
			DirectedAcyclicGraph<IDenotation, IProductionAsEdge> filteredDenotations = 
					TransitionFunctionSupplier.getDenotationGraphFilteredByTreeOfDenotationSets(catTree, denotations);
			/*
			Visualizer.visualizeAttributeGraph(filteredConstructs, "2108141517_atts");
			System.out.println(checkCount);
			*/
			if (!StructureInspector.isARootedInvertedDirectedAcyclicGraph(filteredDenotations)) {
				filteredGraphsAreRootedInvertedDAGs = false;
			}
			checkCount++;
		}
		assertTrue(filteredGraphsAreRootedInvertedDAGs && checkCount > 0);
	}
	
	@Test
	public void whenDenotationGraphFilteredByTreeOfDenotSetsThenSetOfProdSourcesOrTargetsIsDenotSetTreeMinusFramingDenotSet() 
			throws IOException {
		boolean expectedSetOfDenotationSets = true;
		while (conceptTreeSupplier.hasNext()) {
			Tree<IConcept, IIsA> treeOfDenotationSets = conceptTreeSupplier.next();
			Set<IConcept> expectedDenotationSets = treeOfDenotationSets.vertexSet();
			Set<IConcept> returnedDenotationSets = new HashSet<>();
			DirectedAcyclicGraph<IDenotation, IProductionAsEdge> filteredDenotations = 
					TransitionFunctionSupplier.getDenotationGraphFilteredByTreeOfDenotationSets(treeOfDenotationSets, denotations);
			for (IProductionAsEdge productionAsEdge : filteredDenotations.edgeSet()) {
				returnedDenotationSets.add(productionAsEdge.getSourceConcept());
				returnedDenotationSets.add(productionAsEdge.getTargetConcept());
			}
			if (!expectedDenotationSets.equals(returnedDenotationSets)) {
				Set<IConcept> unfoundDenotationSets = Sets.difference(expectedDenotationSets, returnedDenotationSets);
				for (IConcept notFound : unfoundDenotationSets) {
					if (!notFound.isComplementary() && 
							((IComplementaryConcept) notFound).getEmbeddedDenotationSet() != null)
						expectedSetOfDenotationSets = false;
				}
			}
				
		}
		assertTrue(expectedSetOfDenotationSets);
	}	
	
	@Test
	public void whenDenotationGraphIsFilteredByTreeOfDenotSetThenProductionsSourceAndTargetDenotSetsAreRelatedInDenotSetTree() {
		boolean sourceAndTargetDenotSetsAreRelated = true;
		int checkCount = 0;
		while (conceptTreeSupplier.hasNext()) {
			Tree<IConcept, IIsA> treeOfDenotationSets = conceptTreeSupplier.next();
			DirectedAcyclicGraph<IDenotation, IProductionAsEdge> filteredDenotations = 
					TransitionFunctionSupplier.getDenotationGraphFilteredByTreeOfDenotationSets(treeOfDenotationSets, denotations);
			for (IProductionAsEdge productionAsEdge : filteredDenotations.edgeSet()) {
				checkCount++;
				IConcept sourceDenotSet = productionAsEdge.getSourceConcept();
				IConcept targetDenotSet = productionAsEdge.getTargetConcept();
				if (!treeOfDenotationSets.getDescendants(sourceDenotSet).contains(targetDenotSet))
					sourceAndTargetDenotSetsAreRelated = false;
			}
		}
		assertTrue(sourceAndTargetDenotSetsAreRelated && checkCount > 0);
	}
	
	@Test
	public void whenDenotationGraphIsFilteredByTreeOfDenotSetThenSetOfContainingDenotSetsIsDenotSetTreeMinusFramingConcepts() {
		boolean expectedSetOfDenotationSets = true;
		while (conceptTreeSupplier.hasNext()) {
			Tree<IConcept, IIsA> treeOfDenotationSets = conceptTreeSupplier.next();
			Set<IConcept> expectedDenotationSets = treeOfDenotationSets.vertexSet();
			Set<IConcept> returnedDenotationSets = new HashSet<>();
			DirectedAcyclicGraph<IDenotation, IProductionAsEdge> filteredDenotations = 
					TransitionFunctionSupplier.getDenotationGraphFilteredByTreeOfDenotationSets(treeOfDenotationSets, denotations);
			for (IDenotation denotation : filteredDenotations.vertexSet()) {
				returnedDenotationSets.add(denotation.getConcept());
			}
			if (!expectedDenotationSets.equals(returnedDenotationSets)) {
				Set<IConcept> unfoundDenotationSets = Sets.difference(expectedDenotationSets, returnedDenotationSets);
				for (IConcept notFound : unfoundDenotationSets) {
					if (!notFound.isComplementary() 
							&& ((IComplementaryConcept) notFound).getEmbeddedDenotationSet() != null)
						expectedSetOfDenotationSets = false;
				}
			}
		}
		assertTrue(expectedSetOfDenotationSets);
	}
	
}
