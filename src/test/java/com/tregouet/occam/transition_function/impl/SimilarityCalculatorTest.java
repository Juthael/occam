package com.tregouet.occam.transition_function.impl;

import static org.junit.Assert.fail;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.data.categories.ICatTreeSupplier;
import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.categories.impl.Categories;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.data.operators.impl.ProductionBuilder;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.transition_function.IIntentAttTreeSupplier;
import com.tregouet.occam.transition_function.ISimilarityCalculator;
import com.tregouet.occam.transition_function.ITransitionFunction;
import com.tregouet.occam.utils.Visualizer;
import com.tregouet.tree_finder.data.InTree;

public class SimilarityCalculatorTest {

	private static Path shapes1 = Paths.get(".", "src", "test", "java", "files", "shapes1bis.txt");
	private static List<IContextObject> shapes1Obj;
	private static ICategories categories;
	private static DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs = 
			new DirectedAcyclicGraph<>(null, null, false);
	private static ICatTreeSupplier catTreeSupplier;
	private static InTree<ICategory, DefaultEdge> catTree;
	private static DirectedAcyclicGraph<IIntentAttribute, IProduction> filtered_reduced_constructs;
	private static IIntentAttTreeSupplier constrTreeSupplier;
	private static InTree<IIntentAttribute, IProduction> constrTree;
	private static TreeSet<ITransitionFunction> transitionFunctions = new TreeSet<>();
	private static Map<ITransitionFunction, ISimilarityCalculator> tfToSimCalc = new HashMap<>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes1Obj = GenericFileReader.getContextObjects(shapes1);
		categories = new Categories(shapes1Obj);
		List<IProduction> productions = new ProductionBuilder(categories).getProductions();
		productions.stream().forEach(p -> {
			constructs.addVertex(p.getSource());
			constructs.addVertex(p.getTarget());
			constructs.addEdge(p.getSource(), p.getTarget(), p);
		});
		catTreeSupplier = categories.getCatTreeSupplier();
		while (catTreeSupplier.hasNext()) {
			catTree = catTreeSupplier.nextWithTunnelCategoriesRemoved();
			filtered_reduced_constructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByCategoryTree(catTree, constructs);
			constrTreeSupplier = new IntentAttTreeSupplier(filtered_reduced_constructs);
			while (constrTreeSupplier.hasNext()) {
				constrTree = constrTreeSupplier.next();
				ITransitionFunction transitionFunction = 
						new TransitionFunction(shapes1Obj, categories.getObjectCategories(), catTree, constrTree);
				transitionFunctions.add(transitionFunction);
				
				Visualizer.visualizeTransitionFunction(transitionFunction, "2109110911_tf");
				Visualizer.visualizeWeightedTransitionsGraph(transitionFunction.getSimilarityCalculator().getSparseGraph(), "2109110911_sg");
				
				tfToSimCalc.put(transitionFunction, transitionFunction.getSimilarityCalculator());
			}
		}

	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void whenCoherenceScoreRequestedThenReturned() {
		/*
		boolean scoreReturned = true;
		int nbOfChecks = 0;
		for (ISimilarityCalculator simCalc : tfToSimCalc) {
			double coherenceScore = simCalc.getCoherenceScore();
			if (Double.isNaN(coherenceScore) || coherenceScore == 0.0)
				scoreReturned = false;
			nbOfChecks++;
		}
		assertTrue(scoreReturned && nbOfChecks > 0);
		*/
		fail();
	}
	
	@Test
	public void whenSymmetricSimilarityRequestedThenReturned() {
		fail("Not yet implemented");
	}	
	
	@Test
	public void whenAsymmetricSimilarityRequestedThenReturned() {
		fail("Not yet implemented");
	}
	
	@Test
	public void whenPrototypicalityOfAnObjectRequestedThenReturned() {
		fail("Not yet implemented");
	}
	
	@Test
	public void whenProtoypicalityOfACategoryAmongACategorySubsetRequestedThenReturned() {
		fail("Not yet implemented");
	}

}
