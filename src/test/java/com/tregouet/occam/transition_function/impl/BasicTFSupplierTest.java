package com.tregouet.occam.transition_function.impl;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.cost_calculation.PropertyWeighingStrategy;
import com.tregouet.occam.cost_calculation.SimilarityCalculationStrategy;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.concepts.impl.Concepts;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.data.operators.impl.ProductionBuilder;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.io.output.utils.Visualizer;
import com.tregouet.occam.transition_function.IBasicTFSupplier;
import com.tregouet.occam.transition_function.ITransitionFunction;
import com.tregouet.occam.transition_function.TransitionFunctionGraphType;
import com.tregouet.tree_finder.error.InvalidInputException;

@SuppressWarnings("unused")
public class BasicTFSupplierTest {

	private static final Path SHAPES2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static final PropertyWeighingStrategy PROP_WHEIGHING_STRATEGY = 
			PropertyWeighingStrategy.INFORMATIVITY_DIAGNOSTIVITY;
	private static final SimilarityCalculationStrategy SIM_CALCULATION_STRATEGY = 
			SimilarityCalculationStrategy.RATIO_MODEL;
	private static List<IContextObject> shapes2Obj;	
	private IConcepts concepts;
	private final DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs = 
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
	public void whenRequestedThenReturnsTransitionFuncInDecreasingCoherenceScoreOrder() 
			throws IOException, InvalidInputException {
		boolean increasingOrder = true;
		int checkCount = 1;
		IBasicTFSupplier transFuncSupplier = new BasicTFSupplier(concepts, constructs, 
				PROP_WHEIGHING_STRATEGY, SIM_CALCULATION_STRATEGY);
		ITransitionFunction tF = transFuncSupplier.next();
		double prevScore = tF.getCoherenceScore();
		
		Visualizer.visualizeCategoryGraph(concepts.getOntologicalUpperSemilattice(), "2111051132_catUSL");
		System.out.println("TF0 : " + Double.toString(prevScore));
		Visualizer.visualizeTransitionFunction(tF, "2111051132_TFbasicSupp" + Integer.toString(0), 
				TransitionFunctionGraphType.FINITE_AUTOMATON);
		
		while (transFuncSupplier.hasNext()) {
			tF = transFuncSupplier.next();
			double nextScore = tF.getCoherenceScore();
			
			System.out.println("TF" + Integer.toString(checkCount) + " : " + Double.toString(nextScore));
			Visualizer.visualizeCategoryGraph(tF.getCategoryTree(), "2111051132_TFCatTree" +  Integer.toString(checkCount));
			Visualizer.visualizeTransitionFunction(tF, "2111051132_TFbasicSupp" + Integer.toString(checkCount), 
					TransitionFunctionGraphType.FINITE_AUTOMATON);
			
			if (nextScore > prevScore)
				increasingOrder = false;
			prevScore = nextScore;
			checkCount++;
		}
		assertTrue(increasingOrder && checkCount > 0);
	}

}
