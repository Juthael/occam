package com.tregouet.occam.alg.transition_function_gen.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.alg.transition_function_gen.impl.ProductionBuilder;
import com.tregouet.occam.data.languages.specific.ISimpleEdgeProduction;
import com.tregouet.occam.data.languages.specific.ICompositeEdgeProduction;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IContextObject;
import com.tregouet.occam.data.concepts.IDenotation;
import com.tregouet.occam.data.concepts.impl.Concepts;
import com.tregouet.occam.data.languages.specific.IBasicProductionAsEdge;
import com.tregouet.occam.io.input.impl.GenericFileReader;

@SuppressWarnings("unused")
public class ProductionBuilderTest {

	private static final Path SHAPES1 = Paths.get(".", "src", "test", "java", "files", "shapes1.txt");
	private static List<IContextObject> shapes1Obj;
	private IConcepts concepts;
	private ProductionBuilder builder;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes1Obj = GenericFileReader.getContextObjects(SHAPES1);
	}
	
	@Test
	public void noTwoBasicProductionsHaveSameSourceAndTarget() {
		List<ISimpleEdgeProduction> simpleEdgeProductions = builder.getProductions()
				.stream()
				.filter(p -> p instanceof ISimpleEdgeProduction)
				.map(p -> (ISimpleEdgeProduction) p)
				.collect(Collectors.toList());
		Set<List<IDenotation>> sourceTargetPairs = new HashSet<>();
		for (ISimpleEdgeProduction basicProd : simpleEdgeProductions) {
			sourceTargetPairs.add(
					new ArrayList<>(
							Arrays.asList(new IDenotation[] {basicProd.getSource(), basicProd.getTarget()})));
		}
		assertTrue(simpleEdgeProductions.size() == sourceTargetPairs.size());
	}
	
	@Before
	public void setUp() {
		concepts = new Concepts(shapes1Obj);
		builder = new ProductionBuilder(concepts);
		/*
		Categories catImpl = (Categories) categories;
		try {
			Visualizer.visualizeCategoryGraph(catImpl.getCategoryLattice(), "2108040604a");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
	@Test
	public void whenProductionIsCompositeThenAllComponentsHaveSameSourceAndTarget() {
		boolean componentsHaveSameSourceAndTarget = true;
		List<ICompositeEdgeProduction> compositeProds = builder.getProductions()
				.stream()
				.filter(p -> p instanceof ICompositeEdgeProduction)
				.map(p -> (ICompositeEdgeProduction) p)
				.collect(Collectors.toList());
		for (ICompositeEdgeProduction prod : compositeProds) {
			IDenotation prodSource = null;
			IDenotation prodTarget = null;
			for (int i = 0 ; i < prod.getComponents().size() ; i++) {
				if (i == 0) {
					prodSource = prod.getComponents().get(i).getSource();
					prodTarget = prod.getComponents().get(i).getTarget();
				}
				else {
					if (!prod.getComponents().get(i).getSource().equals(prodSource))
						componentsHaveSameSourceAndTarget = false;
					if (!prod.getComponents().get(i).getTarget().equals(prodTarget))
						componentsHaveSameSourceAndTarget = false;
				}
			}
		}
		assertTrue(!compositeProds.isEmpty() && componentsHaveSameSourceAndTarget);
	}

	@Test
	public void whenProductionsRequestedThenAllowGraphBuildingAsExpected() throws IOException {
		boolean aVertexOrEdgeAdditionHasFailed = false;
		List<IBasicProductionAsEdge> basicProductionAsEdges = builder.getProductions()
				.stream()
				.filter(p -> p.getSource().getConcept().type() != IConcept.ABSURDITY)
				.collect(Collectors.toList());
		List<IDenotation> denotations = new ArrayList<>();
		for (IConcept concept : concepts.getTopologicalSorting()) {
			if (concept.type() != IConcept.ABSURDITY)
				denotations.addAll(concept.getDenotations());
		}
		DirectedAcyclicGraph<IDenotation, IBasicProductionAsEdge> graph = new DirectedAcyclicGraph<>(IBasicProductionAsEdge.class);
		for (IDenotation denotation : denotations) {
			if (!graph.addVertex(denotation))
				aVertexOrEdgeAdditionHasFailed = true;
		}
		/*
		for (IProduction production : productions) {
			if (!graph.addEdge(production.getSource(), production.getTarget(), production))
				aVertexOrEdgeAdditionHasFailed = true;
		}
		TransitiveReduction.INSTANCE.reduce(graph);
		Visualizer.visualizeAttributeGraph(graph, "2108040604b");
		*/
		assertFalse(aVertexOrEdgeAdditionHasFailed);
	}

}
