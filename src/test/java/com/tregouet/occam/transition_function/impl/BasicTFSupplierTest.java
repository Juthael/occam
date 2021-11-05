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

import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.categories.impl.Categories;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.data.operators.impl.ProductionBuilder;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.io.output.utils.Visualizer;
import com.tregouet.occam.transition_function.IBasicTFSupplier;
import com.tregouet.occam.transition_function.ITransitionFunction;
import com.tregouet.tree_finder.error.InvalidInputException;

@SuppressWarnings("unused")
public class BasicTFSupplierTest {

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
	public void whenRequestedThenReturnsTransitionFuncInDecreasingCoherenceScoreOrder() 
			throws IOException, InvalidInputException {
		boolean increasingOrder = true;
		int checkCount = 1;
		IBasicTFSupplier transFuncSupplier = new BasicTFSupplier(categories, constructs);
		ITransitionFunction tF = transFuncSupplier.next();
		double prevScore = tF.getCoherenceScore();
		/*
		System.out.println("TF0 : " + Double.toString(prevScore));
		Visualizer.visualizeTransitionFunction(tF, "2109251004_TFbasicSupp0" + Integer.toString(0), true);
		*/
		while (transFuncSupplier.hasNext()) {
			tF = transFuncSupplier.next();
			double nextScore = tF.getCoherenceScore();
			/*
			System.out.println("TF" + Integer.toString(checkCount) + " : " + Double.toString(prevScore));
			Visualizer.visualizeTransitionFunction(tF, "2109251004_TFbasicSupp0" + Integer.toString(checkCount), true);
			*/
			if (nextScore > prevScore)
				increasingOrder = false;
			prevScore = nextScore;
			checkCount++;
			/*
			System.out.println(checkCount + " : " + Double.toString(nextScore));
			*/
		}
		assertTrue(increasingOrder && checkCount > 0);
	}

}
