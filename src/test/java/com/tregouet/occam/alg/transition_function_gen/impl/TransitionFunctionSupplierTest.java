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
import com.tregouet.occam.alg.builders.preconcepts.IPreconceptTreeSupplier;
import com.tregouet.occam.alg.builders.representations.properties.transitions.impl.ProductionSetBuilder;
import com.tregouet.occam.alg.scoring_dep.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring_dep.ScoringStrategy_dep;
import com.tregouet.occam.alg.transition_function_gen.impl.TransitionFunctionSupplier;
import com.tregouet.occam.data.languages.specific.IStronglyContextualized;
import com.tregouet.occam.data.preconcepts.IComplementaryPreconcept;
import com.tregouet.occam.data.preconcepts.IContextObject;
import com.tregouet.occam.data.preconcepts.IDenotation;
import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.preconcepts.IPreconcepts;
import com.tregouet.occam.data.preconcepts.impl.Preconcepts;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.StructureInspector;

@SuppressWarnings("unused")
public class TransitionFunctionSupplierTest {

	private static final Path SHAPES2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;	
	private IPreconcepts preconcepts;
	private IPreconceptTreeSupplier preconceptTreeSupplier;
	private DirectedAcyclicGraph<IDenotation, IStronglyContextualized> denotations = 
			new DirectedAcyclicGraph<>(null, null, false);
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(SHAPES2);
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy_dep.SCORING_STRATEGY_1);
	}

	@Before
	public void setUp() throws Exception {
		preconcepts = new Preconcepts(shapes2Obj);
		List<IStronglyContextualized> stronglyContextualizeds = new ProductionSetBuilder(preconcepts).getProductions();
		stronglyContextualizeds.stream().forEach(p -> {
			denotations.addVertex(p.getSource());
			denotations.addVertex(p.getTarget());
			denotations.addEdge(p.getSource(), p.getTarget(), p);
		});
		preconceptTreeSupplier = preconcepts.getConceptTreeSupplier();
	}

	@Test
	public void whenDenotationGraphFilteredByTreeOfDenotSetsThenOrderedSetOfDenotationsIsARootedInvertedDAG() throws IOException {
		boolean filteredGraphsAreRootedInvertedDAGs = true;
		int checkCount = 0;
		while (preconceptTreeSupplier.hasNext() && filteredGraphsAreRootedInvertedDAGs) {
			Tree<IPreconcept, IIsA> catTree = preconceptTreeSupplier.next();
			/*
			Visualizer.visualizeCategoryGraph(catTree, "2108141517_cats");
			*/
			DirectedAcyclicGraph<IDenotation, IStronglyContextualized> filteredDenotations = 
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
		while (preconceptTreeSupplier.hasNext()) {
			Tree<IPreconcept, IIsA> treeOfDenotationSets = preconceptTreeSupplier.next();
			Set<IPreconcept> expectedDenotationSets = treeOfDenotationSets.vertexSet();
			Set<IPreconcept> returnedDenotationSets = new HashSet<>();
			DirectedAcyclicGraph<IDenotation, IStronglyContextualized> filteredDenotations = 
					TransitionFunctionSupplier.getDenotationGraphFilteredByTreeOfDenotationSets(treeOfDenotationSets, denotations);
			for (IStronglyContextualized stronglyContextualized : filteredDenotations.edgeSet()) {
				returnedDenotationSets.add(stronglyContextualized.getSourceConcept());
				returnedDenotationSets.add(stronglyContextualized.getTargetConcept());
			}
			if (!expectedDenotationSets.equals(returnedDenotationSets)) {
				Set<IPreconcept> unfoundDenotationSets = Sets.difference(expectedDenotationSets, returnedDenotationSets);
				for (IPreconcept notFound : unfoundDenotationSets) {
					if (!notFound.isComplementary() && 
							((IComplementaryPreconcept) notFound).getEmbeddedDenotationSet() != null)
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
		while (preconceptTreeSupplier.hasNext()) {
			Tree<IPreconcept, IIsA> treeOfDenotationSets = preconceptTreeSupplier.next();
			DirectedAcyclicGraph<IDenotation, IStronglyContextualized> filteredDenotations = 
					TransitionFunctionSupplier.getDenotationGraphFilteredByTreeOfDenotationSets(treeOfDenotationSets, denotations);
			for (IStronglyContextualized stronglyContextualized : filteredDenotations.edgeSet()) {
				checkCount++;
				IPreconcept sourceDenotSet = stronglyContextualized.getSourceConcept();
				IPreconcept targetDenotSet = stronglyContextualized.getTargetConcept();
				if (!treeOfDenotationSets.getDescendants(sourceDenotSet).contains(targetDenotSet))
					sourceAndTargetDenotSetsAreRelated = false;
			}
		}
		assertTrue(sourceAndTargetDenotSetsAreRelated && checkCount > 0);
	}
	
	@Test
	public void whenDenotationGraphIsFilteredByTreeOfDenotSetThenSetOfContainingDenotSetsIsDenotSetTreeMinusFramingConcepts() {
		boolean expectedSetOfDenotationSets = true;
		while (preconceptTreeSupplier.hasNext()) {
			Tree<IPreconcept, IIsA> treeOfDenotationSets = preconceptTreeSupplier.next();
			Set<IPreconcept> expectedDenotationSets = treeOfDenotationSets.vertexSet();
			Set<IPreconcept> returnedDenotationSets = new HashSet<>();
			DirectedAcyclicGraph<IDenotation, IStronglyContextualized> filteredDenotations = 
					TransitionFunctionSupplier.getDenotationGraphFilteredByTreeOfDenotationSets(treeOfDenotationSets, denotations);
			for (IDenotation denotation : filteredDenotations.vertexSet()) {
				returnedDenotationSets.add(denotation.getPreconcept());
			}
			if (!expectedDenotationSets.equals(returnedDenotationSets)) {
				Set<IPreconcept> unfoundDenotationSets = Sets.difference(expectedDenotationSets, returnedDenotationSets);
				for (IPreconcept notFound : unfoundDenotationSets) {
					if (!notFound.isComplementary() 
							&& ((IComplementaryPreconcept) notFound).getEmbeddedDenotationSet() != null)
						expectedSetOfDenotationSets = false;
				}
			}
		}
		assertTrue(expectedSetOfDenotationSets);
	}
	
}
