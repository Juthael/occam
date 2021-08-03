package com.tregouet.occam.data.operators.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.graph.DirectedMultigraph;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.categories.impl.Categories;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.utils.Visualizer;

@SuppressWarnings("unused")
public class ProductionBuilderTest {

	private static Path shapes0 = Paths.get(".", "src", "test", "java", "files", "shapes0.txt");
	private static List<IContextObject> shapes0Obj;
	private static ICategories categories;
	private static ProductionBuilder builder;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes0Obj = GenericFileReader.getContextObjects(shapes0);
		categories = new Categories(shapes0Obj);
		/*
		Categories catImpl = (Categories) categories;
		Visualizer.visualizeCategoryGraph(catImpl.getDiagram(), "2108031217a");
		*/
	}

	@Test
	public void whenProductionsRequestedThenReturnedAndAllowsGraphBuilding() throws IOException {
		builder = new ProductionBuilder(categories);
		List<IProduction> productions = builder.getProductions()
				.stream()
				.filter(p -> p.getOperatorInput().getCategory().type() != ICategory.ABSURDITY)
				.collect(Collectors.toList());
		List<IIntentAttribute> attributes = new ArrayList<>();
		for (ICategory category : categories.getTopologicalSorting()) {
			if (category.type() != ICategory.ABSURDITY)
				attributes.addAll(category.getIntent());
		}
		DirectedMultigraph<IIntentAttribute, IProduction> graph = new DirectedMultigraph<>(IProduction.class);
		for (IIntentAttribute attribute : attributes)
			graph.addVertex(attribute);
		for (IProduction production : productions)
			graph.addEdge(production.getOperatorInput(), production.getOperatorOutput(), production);
		TransitiveReduction.INSTANCE.reduce(graph);
		/*
		Visualizer.visualizeAttributeGraph(graph, "2108031217b");
		*/
	}

}
