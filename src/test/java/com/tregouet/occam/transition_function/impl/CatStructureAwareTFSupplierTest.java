package com.tregouet.occam.transition_function.impl;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.categories.impl.Categories;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.data.operators.impl.ProductionBuilder;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.io.output.utils.Visualizer;
import com.tregouet.occam.transition_function.ICatStructureAwareTFSupplier;
import com.tregouet.occam.transition_function.IRepresentedCatTree;
import com.tregouet.tree_finder.error.InvalidInputException;

public class CatStructureAwareTFSupplierTest {
	
	private static final Path shapes2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;	
	private static ICategories categories;
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
	}

	@Test
	public void whenRequestedThenReturnsRepresentedCatStructuresInDecreasingCoherenceOrder() 
			throws IOException, InvalidInputException {
		boolean increasingOrder = true;
		int idx = 0;
		ICatStructureAwareTFSupplier transFuncSupplier = new CatStructureAwareTFSupplier(categories, constructs);
		List<Double> coherenceScores = new ArrayList<>();
		IRepresentedCatTree representedCatTree;
		/*
		System.out.println(transFuncSupplier.getDefinitionOfObjects() + System.lineSeparator());
		Visualizer.visualizeCategoryGraph(categories.getTransitiveReduction(), "2109250747_CL");
		*/
		while (transFuncSupplier.hasNext()) {
			representedCatTree = transFuncSupplier.next();
			coherenceScores.add(representedCatTree.getCoherenceScore());
			
			System.out.println("***Transition Function N° " + Integer.toString(idx) + " : " 
					+ Double.toString(representedCatTree.getCoherenceScore()));			
			Visualizer.visualizeCategoryGraph(representedCatTree.getCategoryTree(), 
					"2109250747_CT_" + Integer.toString(idx));
			Visualizer.visualizeTransitionFunction(
					representedCatTree.getTransitionFunction(), 
					"2109250747_TF" + Integer.toString(idx), 
					true);
			
			idx++;
		}
		for (int i = 0 ; i < coherenceScores.size() - 1 ; i++) {
			if (coherenceScores.get(i) < coherenceScores.get(i + 1))
				increasingOrder = false;
		}
		assertTrue(increasingOrder && idx > 0);
	}

}
