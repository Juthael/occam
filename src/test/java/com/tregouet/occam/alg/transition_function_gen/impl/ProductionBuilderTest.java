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
import com.tregouet.occam.data.abstract_machines.transitions.IBasicProduction;
import com.tregouet.occam.data.abstract_machines.transitions.ICompositeProduction;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IIntentConstruct;
import com.tregouet.occam.data.concepts.impl.Concepts;
import com.tregouet.occam.data.languages.generic.IContextObject;
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
		List<IBasicProduction> basicProductions = builder.getProductions()
				.stream()
				.filter(p -> p instanceof IBasicProduction)
				.map(p -> (IBasicProduction) p)
				.collect(Collectors.toList());
		Set<List<IIntentConstruct>> sourceTargetPairs = new HashSet<>();
		for (IBasicProduction basicProd : basicProductions) {
			sourceTargetPairs.add(
					new ArrayList<>(
							Arrays.asList(new IIntentConstruct[] {basicProd.getSource(), basicProd.getTarget()})));
		}
		assertTrue(basicProductions.size() == sourceTargetPairs.size());
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
		List<ICompositeProduction> compositeProds = builder.getProductions()
				.stream()
				.filter(p -> p instanceof ICompositeProduction)
				.map(p -> (ICompositeProduction) p)
				.collect(Collectors.toList());
		for (ICompositeProduction prod : compositeProds) {
			IIntentConstruct prodSource = null;
			IIntentConstruct prodTarget = null;
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
		List<IProduction> productions = builder.getProductions()
				.stream()
				.filter(p -> p.getSource().getConcept().type() != IConcept.ABSURDITY)
				.collect(Collectors.toList());
		List<IIntentConstruct> attributes = new ArrayList<>();
		for (IConcept concept : concepts.getTopologicalSorting()) {
			if (concept.type() != IConcept.ABSURDITY)
				attributes.addAll(concept.getIntent());
		}
		DirectedAcyclicGraph<IIntentConstruct, IProduction> graph = new DirectedAcyclicGraph<>(IProduction.class);
		for (IIntentConstruct attribute : attributes) {
			if (!graph.addVertex(attribute))
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
