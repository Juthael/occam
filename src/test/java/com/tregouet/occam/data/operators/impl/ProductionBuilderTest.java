package com.tregouet.occam.data.operators.impl;

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

import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.graph.DirectedMultigraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.categories.impl.Categories;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IBasicProduction;
import com.tregouet.occam.data.operators.ICompositeProduction;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.io.output.utils.Visualizer;

@SuppressWarnings("unused")
public class ProductionBuilderTest {

	private static final Path SHAPES1 = Paths.get(".", "src", "test", "java", "files", "shapes1.txt");
	private static List<IContextObject> shapes1Obj;
	private ICategories categories;
	private ProductionBuilder builder;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes1Obj = GenericFileReader.getContextObjects(SHAPES1);
	}
	
	@Before
	public void setUp() {
		categories = new Categories(shapes1Obj);
		builder = new ProductionBuilder(categories);
		/*
		Categories catImpl = (Categories) categories;
		Visualizer.visualizeCategoryGraph(catImpl.getDiagram(), "2108040604a");
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
			IIntentAttribute prodSource = null;
			IIntentAttribute prodTarget = null;
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
	public void noTwoBasicProductionsHaveSameSourceAndTarget() {
		List<IBasicProduction> basicProductions = builder.getProductions()
				.stream()
				.filter(p -> p instanceof IBasicProduction)
				.map(p -> (IBasicProduction) p)
				.collect(Collectors.toList());
		Set<List<IIntentAttribute>> sourceTargetPairs = new HashSet<>();
		for (IBasicProduction basicProd : basicProductions) {
			sourceTargetPairs.add(
					new ArrayList<>(
							Arrays.asList(new IIntentAttribute[] {basicProd.getSource(), basicProd.getTarget()})));
		}
		assertTrue(basicProductions.size() == sourceTargetPairs.size());
	}

	@Test
	public void whenProductionsRequestedThenAllowGraphBuildingAsExpected() throws IOException {
		boolean aVertexOrEdgeAdditionHasFailed = false;
		List<IProduction> productions = builder.getProductions()
				.stream()
				.filter(p -> p.getSource().getCategory().type() != ICategory.ABSURDITY)
				.collect(Collectors.toList());
		List<IIntentAttribute> attributes = new ArrayList<>();
		for (ICategory category : categories.getTopologicalSorting()) {
			if (category.type() != ICategory.ABSURDITY)
				attributes.addAll(category.getIntent());
		}
		DirectedAcyclicGraph<IIntentAttribute, IProduction> graph = new DirectedAcyclicGraph<>(IProduction.class);
		for (IIntentAttribute attribute : attributes) {
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
