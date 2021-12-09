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

import com.tregouet.occam.cost_calculation.SimilarityCalculationStrategy;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.concepts.impl.Concepts;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.transitions.IProduction;
import com.tregouet.occam.data.transitions.impl.ProductionBuilder;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.io.output.utils.Visualizer;
import com.tregouet.occam.transition_function.ICatStructureAwareTFSupplier;
import com.tregouet.occam.transition_function.IRepresentedCatTree;
import com.tregouet.occam.transition_function.TransitionFunctionGraphType;

@SuppressWarnings("unused")
public class CatStructureAwareTFSupplierTest {
	
	private static final Path SHAPES2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static final SimilarityCalculationStrategy SIM_CALCULATION_STRATEGY = 
			SimilarityCalculationStrategy.RATIO_MODEL;
	private static List<IContextObject> shapes2Obj;	
	private IConcepts concepts;
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
	}

	@Test
	public void whenRequestedThenReturnsRepresentedCatStructuresInDecreasingCoherenceOrder() 
			throws IOException {
		/*
		Visualizer.visualizeCategoryGraph(concepts.getTransitiveReduction(), "211201_CL");
		*/
		boolean increasingOrder = true;
		int idx = 0;
		ICatStructureAwareTFSupplier transFuncSupplier = new CatStructureAwareTFSupplier(concepts, constructs, 
				SIM_CALCULATION_STRATEGY);
		List<Double> coherenceScores = new ArrayList<>();
		IRepresentedCatTree representedCatTree;
		while (transFuncSupplier.hasNext()) {
			representedCatTree = transFuncSupplier.next();
			coherenceScores.add(representedCatTree.getCoherenceScore());
			/*
			System.out.println("***Transition Function N° " + Integer.toString(idx) + " : " 
					+ Double.toString(representedCatTree.getCoherenceScore()));			
			Visualizer.visualizeCategoryGraph(representedCatTree.getCategoryTree(), 
					"2109250747_CT_" + Integer.toString(idx));
			Visualizer.visualizeTransitionFunction(
					representedCatTree.getTransitionFunction(), 
					"2109250747_TF" + Integer.toString(idx), 
					TransitionFunctionGraphType.FINITE_AUTOMATON);
			*/
			idx++;
		}
		for (int i = 0 ; i < coherenceScores.size() - 1 ; i++) {
			if (coherenceScores.get(i) < coherenceScores.get(i + 1))
				increasingOrder = false;
		}
		assertTrue(increasingOrder && idx > 0);
	}

}
