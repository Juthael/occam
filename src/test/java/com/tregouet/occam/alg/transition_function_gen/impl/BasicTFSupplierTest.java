package com.tregouet.occam.alg.transition_function_gen.impl;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.alg.builders.representations.productions.impl.IfIsAThenBuildProductions;
import com.tregouet.occam.alg.scoring_dep.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring_dep.ScoringStrategy_dep;
import com.tregouet.occam.alg.transition_function_gen_dep.IBasicTFSupplier;
import com.tregouet.occam.alg.transition_function_gen_dep.impl.BasicTFSupplier;
import com.tregouet.occam.data.languages.words.fact.IStronglyContextualized;
import com.tregouet.occam.data.logical_structures.automata.IAutomaton;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IDenotation;
import com.tregouet.occam.data.representations.concepts.impl.ConceptLattice;
import com.tregouet.occam.io.input.impl.GenericFileReader;

@SuppressWarnings("unused")
public class BasicTFSupplierTest {

	private static final Path SHAPES2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;	
	private IConceptLattice conceptLattice;
	private final DirectedAcyclicGraph<IDenotation, IStronglyContextualized> denotations = 
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
	}

	@Test
	public void whenRequestedThenReturnsTransitionFuncInDecreasingCoherenceScoreOrder() 
			throws IOException {
		boolean increasingOrder = true;
		int checkCount = 1;
		IBasicTFSupplier transFuncSupplier = new BasicTFSupplier(conceptLattice, denotations);
		IAutomaton tF = transFuncSupplier.next();
		double prevScore = tF.getScore();
		/*
		Visualizer.visualizeCategoryGraph(concepts.getOntologicalUpperSemilattice(), "2111051132_catUSL");
		System.out.println("TF0 : " + Double.toString(prevScore));
		Visualizer.visualizeTransitionFunction(tF, "2111051132_TFbasicSupp" + Integer.toString(0), 
				TransitionFunctionGraphType.FINITE_AUTOMATON);
		*/
		while (transFuncSupplier.hasNext()) {
			tF = transFuncSupplier.next();
			double nextScore = tF.getScore();
			/*
			System.out.println("TF" + Integer.toString(checkCount) + " : " + Double.toString(nextScore));
			Visualizer.visualizeCategoryGraph(tF.getCategoryTree(), "2111051132_TFCatTree" +  Integer.toString(checkCount));
			Visualizer.visualizeTransitionFunction(tF, "2111051132_TFbasicSupp" + Integer.toString(checkCount), 
					TransitionFunctionGraphType.FINITE_AUTOMATON);
			*/
			if (nextScore > prevScore)
				increasingOrder = false;
			prevScore = nextScore;
			checkCount++;
		}
		assertTrue(increasingOrder && checkCount > 0);
	}

}
