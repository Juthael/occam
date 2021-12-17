package com.tregouet.occam.alg.transition_function_gen.impl;

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

import com.tregouet.occam.alg.calculators.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.calculators.ScoringStrategy;
import com.tregouet.occam.alg.calculators.scores.SimilarityScoringStrategy;
import com.tregouet.occam.alg.transition_function_gen.IConceptStructureBasedTFSupplier;
import com.tregouet.occam.alg.transition_function_gen.impl.ConceptStructureBasedTFSupplier;
import com.tregouet.occam.alg.transition_function_gen.impl.ProductionBuilder;
import com.tregouet.occam.data.abstract_machines.functions.ISetOfRelatedTransFunctions;
import com.tregouet.occam.data.abstract_machines.functions.TransitionFunctionGraphType;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IIntentConstruct;
import com.tregouet.occam.data.concepts.impl.Concepts;
import com.tregouet.occam.data.languages.generic.IContextObject;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.io.output.utils.Visualizer;

@SuppressWarnings("unused")
public class ConceptStructureBasedTFSupplierTest {
	
	private static final Path SHAPES2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;	
	private IConcepts concepts;
	private DirectedAcyclicGraph<IIntentConstruct, IProduction> constructs = 
			new DirectedAcyclicGraph<>(null, null, false);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(SHAPES2);
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy.CONCEPTUAL_COHERENCE);
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
		IConceptStructureBasedTFSupplier transFuncSupplier = new ConceptStructureBasedTFSupplier(concepts, constructs);
		List<Double> coherenceScores = new ArrayList<>();
		ISetOfRelatedTransFunctions setOfRelatedTransFunctions;
		while (transFuncSupplier.hasNext()) {
			setOfRelatedTransFunctions = transFuncSupplier.next();
			coherenceScores.add(setOfRelatedTransFunctions.getCoherenceScore());
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
