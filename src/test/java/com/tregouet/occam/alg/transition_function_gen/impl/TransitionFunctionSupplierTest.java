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
import com.tregouet.occam.alg.builders.representations.concept_trees.ConceptTreeBuilder;
import com.tregouet.occam.alg.builders.representations.productions.impl.IfIsAThenBuildProductions;
import com.tregouet.occam.alg.scoring_dep.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring_dep.ScoringStrategy_dep;
import com.tregouet.occam.alg.transition_function_gen_dep.impl.TransitionFunctionSupplier;
import com.tregouet.occam.data.languages.words.fact.IStronglyContextualized;
import com.tregouet.occam.data.representations.concepts.IComplementaryConcept;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IDenotation;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.concepts.impl.ConceptLattice;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.InvertedTree;
import com.tregouet.tree_finder.utils.StructureInspector;

@SuppressWarnings("unused")
public class TransitionFunctionSupplierTest {

	private static final Path SHAPES2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;	
	private IConceptLattice conceptLattice;
	private ConceptTreeBuilder conceptTreeBuilder;
	private DirectedAcyclicGraph<IDenotation, IStronglyContextualized> denotations = 
			new DirectedAcyclicGraph<>(null, null, false);
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(SHAPES2);
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy_dep.SCORING_STRATEGY_1);
	}

	@Before
	public void setUp() throws Exception {
		conceptLattice = new ConceptLattice(shapes2Obj);
		List<IStronglyContextualized> stronglyContextualizeds = new IfIsAThenBuildProductions(conceptLattice).getProductions();
		stronglyContextualizeds.stream().forEach(p -> {
			denotations.addVertex(p.getSource());
			denotations.addVertex(p.getTarget());
			denotations.addEdge(p.getSource(), p.getTarget(), p);
		});
		conceptTreeBuilder = conceptLattice.getConceptTreeSupplier();
	}

	@Test
	public void whenDenotationGraphFilteredByTreeOfDenotSetsThenOrderedSetOfDenotationsIsARootedInvertedDAG() throws IOException {
		boolean filteredGraphsAreRootedInvertedDAGs = true;
		int checkCount = 0;
		while (conceptTreeBuilder.hasNext() && filteredGraphsAreRootedInvertedDAGs) {
			InvertedTree<IConcept, IIsA> catTree = conceptTreeBuilder.next();
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
		while (conceptTreeBuilder.hasNext()) {
			InvertedTree<IConcept, IIsA> treeOfDenotationSets = conceptTreeBuilder.next();
			Set<IConcept> expectedDenotationSets = treeOfDenotationSets.vertexSet();
			Set<IConcept> returnedDenotationSets = new HashSet<>();
			DirectedAcyclicGraph<IDenotation, IStronglyContextualized> filteredDenotations = 
					TransitionFunctionSupplier.getDenotationGraphFilteredByTreeOfDenotationSets(treeOfDenotationSets, denotations);
			for (IStronglyContextualized stronglyContextualized : filteredDenotations.edgeSet()) {
				returnedDenotationSets.add(stronglyContextualized.getSourceConcept());
				returnedDenotationSets.add(stronglyContextualized.getTargetConcept());
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
		while (conceptTreeBuilder.hasNext()) {
			InvertedTree<IConcept, IIsA> treeOfDenotationSets = conceptTreeBuilder.next();
			DirectedAcyclicGraph<IDenotation, IStronglyContextualized> filteredDenotations = 
					TransitionFunctionSupplier.getDenotationGraphFilteredByTreeOfDenotationSets(treeOfDenotationSets, denotations);
			for (IStronglyContextualized stronglyContextualized : filteredDenotations.edgeSet()) {
				checkCount++;
				IConcept sourceDenotSet = stronglyContextualized.getSourceConcept();
				IConcept targetDenotSet = stronglyContextualized.getTargetConcept();
				if (!treeOfDenotationSets.getDescendants(sourceDenotSet).contains(targetDenotSet))
					sourceAndTargetDenotSetsAreRelated = false;
			}
		}
		assertTrue(sourceAndTargetDenotSetsAreRelated && checkCount > 0);
	}
	
	@Test
	public void whenDenotationGraphIsFilteredByTreeOfDenotSetThenSetOfContainingDenotSetsIsDenotSetTreeMinusFramingConcepts() {
		boolean expectedSetOfDenotationSets = true;
		while (conceptTreeBuilder.hasNext()) {
			InvertedTree<IConcept, IIsA> treeOfDenotationSets = conceptTreeBuilder.next();
			Set<IConcept> expectedDenotationSets = treeOfDenotationSets.vertexSet();
			Set<IConcept> returnedDenotationSets = new HashSet<>();
			DirectedAcyclicGraph<IDenotation, IStronglyContextualized> filteredDenotations = 
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
