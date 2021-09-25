package com.tregouet.occam.transition_function.impl;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.data.categories.IClassificationTreeSupplier;
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
import com.tregouet.tree_finder.data.InTree;

public class SimilarityCalculatorTest {

	private static Path shapes2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;
	private static ICategories categories;
	private static DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs = 
			new DirectedAcyclicGraph<>(null, null, false);
	private static IClassificationTreeSupplier classificationTreeSupplier;
	private static InTree<ICategory, DefaultEdge> catTree;
	private static DirectedAcyclicGraph<IIntentAttribute, IProduction> filtered_reduced_constructs;
	private static IIntentAttTreeSupplier constrTreeSupplier;
	private static InTree<IIntentAttribute, IProduction> constrTree;
	private static TreeSet<ITransitionFunction> transitionFunctions = new TreeSet<>();
	private static Map<ITransitionFunction, ISimilarityCalculator> tfToSimCalc = new HashMap<>();
	
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
		classificationTreeSupplier = categories.getCatTreeSupplier();
		while (classificationTreeSupplier.hasNext()) {
			catTree = classificationTreeSupplier.next();
			filtered_reduced_constructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByCategoryTreeThenReduced(catTree, constructs);
			constrTreeSupplier = new IntentAttTreeSupplier(filtered_reduced_constructs);
			while (constrTreeSupplier.hasNext()) {
				constrTree = constrTreeSupplier.next();
				ITransitionFunction transitionFunction = 
						new TransitionFunction(shapes2Obj, categories.getObjectCategories(), catTree, constrTree);
				transitionFunctions.add(transitionFunction);
				/*
				Visualizer.visualizeTransitionFunction(transitionFunction, "2109110911_tf");
				Visualizer.visualizeWeightedTransitionsGraph(transitionFunction.getSimilarityCalculator().getSparseGraph(), "2109110911_sg");
				*/
				tfToSimCalc.put(transitionFunction, transitionFunction.getSimilarityCalculator());
			}
		}

	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void whenCoherenceScoreRequestedThenReturned() {
		boolean scoreReturned = true;
		int nbOfChecks = 0;
		for (ITransitionFunction tF : transitionFunctions) {
			double coherenceScore = tfToSimCalc.get(tF).getCoherenceScore();
			if (Double.isNaN(coherenceScore) || coherenceScore == 0.0)
				scoreReturned = false;
			nbOfChecks++;
		}
		assertTrue(scoreReturned && nbOfChecks > 0);
	}
	
	@Test
	public void whenSymmetricSimilarityRequestedThenReturned() throws IOException {
		boolean returned = true;
		int nbOfChecks = 0;
		for (ITransitionFunction tF : transitionFunctions) {
			SimilarityCalculator calculator = (SimilarityCalculator) tF.getSimilarityCalculator();
			/*
			System.out.println("***NEW TRANSITION FUNCTION***" + System.lineSeparator());
			Visualizer.visualizeTransitionFunction(tF, "2109161427_tf", true);
			Visualizer.visualizeWeightedTransitionsGraph(calculator.getSparseGraph(), "2109161427_sg");
			*/			
			int[] objects = new int[tF.getCategoryTree().getLeaves().size()];
			for (int i = 0 ; i < objects.length ; i++) {
				objects[i] = tF.getCategoryTree().getLeaves().get(i).getID();
			}
			for (int j = 0 ; j < objects.length - 1 ; j++) {
				for (int k = j+1 ; k < objects.length ; k++) {
					double similarity = calculator.howSimilar(objects[j], objects[k]);
					/*
					System.out.println("OBJ 1 : ");
					System.out.println(tF.getCategoryTree().getLeaves().get(j).toString() + System.lineSeparator());
					System.out.println("OBJ 2 : ");
					System.out.println(tF.getCategoryTree().getLeaves().get(k).toString() + System.lineSeparator());
					System.out.println("Similarity : " + 
							Double.toString(similarity) + System.lineSeparator() + System.lineSeparator());
					*/
					if (Double.isNaN(similarity))
						returned = false;
					nbOfChecks++;
				}
			}
		}
		assertTrue(returned && nbOfChecks > 0);
	}	
	
	@Test
	public void whenAsymmetricSimilarityRequestedThenReturned() throws IOException {
		boolean returned = true;
		int nbOfChecks = 0;
		for (ITransitionFunction tF : transitionFunctions) {
			SimilarityCalculator calculator = (SimilarityCalculator) tF.getSimilarityCalculator();
			/*
			System.out.println("***NEW TRANSITION FUNCTION***" + System.lineSeparator());
			Visualizer.visualizeTransitionFunction(tF, "2109161427_tf", true);
			Visualizer.visualizeWeightedTransitionsGraph(calculator.getSparseGraph(), "2109161427_sg");
			*/
			int[] objects = new int[tF.getCategoryTree().getLeaves().size()];
			for (int i = 0 ; i < objects.length ; i++) {
				objects[i] = tF.getCategoryTree().getLeaves().get(i).getID();
			}
			for (int j = 0 ; j < objects.length ; j++) {
				for (int k = 0 ; k < objects.length ; k++) {
					double similarity = calculator.howSimilarTo(objects[j], objects[k]);
					/*
					System.out.println("OBJ 1 : ");
					System.out.println(tF.getCategoryTree().getLeaves().get(j).toString() + System.lineSeparator());
					System.out.println("OBJ 2 : ");
					System.out.println(tF.getCategoryTree().getLeaves().get(k).toString() + System.lineSeparator());
					System.out.println("Resemblance of OBJ1 to OBJ2 : " + 
							Double.toString(similarity) + System.lineSeparator() + System.lineSeparator());
					*/
					if (Double.isNaN(similarity))
						returned = false;
					nbOfChecks++;
				}
			}
		}
		assertTrue(returned && nbOfChecks > 0);
	}
	
	@Test
	public void whenPrototypicalityOfAnObjectRequestedThenReturned() {
		boolean returned = true;
		int nbOfChecks = 0;
		for (ITransitionFunction tF : transitionFunctions) {
			SimilarityCalculator calculator = (SimilarityCalculator) tF.getSimilarityCalculator();
			/*
			System.out.println("***NEW TRANSITION FUNCTION***" + System.lineSeparator());
			Visualizer.visualizeTransitionFunction(tF, "2109161427_tf", true);
			Visualizer.visualizeWeightedTransitionsGraph(calculator.getSparseGraph(), "2109161427_sg");
			*/
			int[] objects = new int[tF.getCategoryTree().getLeaves().size()];
			for (int i = 0 ; i < objects.length ; i++) {
				objects[i] = tF.getCategoryTree().getLeaves().get(i).getID();				
			}
			for (int j = 0 ; j < objects.length ; j++) {
				double prototypicality = calculator.howProtoypical(objects[j]);
				if (Double.isNaN(prototypicality))
					returned = false;
				/*
				System.out.println("OBJ : ");
				System.out.println(tF.getCategoryTree().getLeaves().get(j).toString() + System.lineSeparator());
				System.out.println("Prototypicality : " + 
						Double.toString(prototypicality) + System.lineSeparator() + System.lineSeparator());
				*/
				nbOfChecks++;
			}
		}
		assertTrue(returned && nbOfChecks > 0);
	}
	
	@Test
	public void whenProtoypicalityOfACategoryAmongACategorySubsetRequestedThenReturned() {
		boolean returned = true;
		int nbOfChecks = 0;
		Iterator<ITransitionFunction> tFIte = transitionFunctions.iterator();
		while (tFIte.hasNext() && nbOfChecks < 50000) {
			ITransitionFunction tF = tFIte.next();
			ISimilarityCalculator calculator = tF.getSimilarityCalculator();
			List<ICategory> categorySet = tF.getCategoryTree().getTopologicalSortingOfVertices();
			List<List<ICategory>> categoryPowerSet = buildCategoryPowerSet(categorySet);
			for (ICategory cat : categorySet) {
				for (List<ICategory> subset : categoryPowerSet) {
					if (!subset.isEmpty() && (subset.size() > 1 || (!subset.get(0).equals(cat)))) {
						int[] subsetIDs = new int[subset.size()];
						for (int i = 0 ; i < subset.size() ; i++)
							subsetIDs[i] = subset.get(i).getID();
						double prototypicality = calculator.howPrototypicalAmong(cat.getID(), subsetIDs);
						if (Double.isNaN(prototypicality))
							returned = false;
						nbOfChecks++;
					}
				}
			}
		}
		assertTrue(returned && nbOfChecks > 0);
	}
	
	@Test
	public void whenReacheableEdgesRequestedThenExpectedReturned() {
		boolean asExpected = true;
		int nbOfChecks = 0;
		for (ITransitionFunction tF : transitionFunctions) {
			int rootID = tF.getCategoryTree().getRoot().getID();
			int[] leavesID = new int[tF.getCategoryTree().getLeaves().size()];
			for (int i = 0 ; i < leavesID.length ; i++) {
				leavesID[i] = tF.getCategoryTree().getLeaves().get(i).getID();
			}
			SimilarityCalculator calculator = (SimilarityCalculator) tfToSimCalc.get(tF);
			for (Integer leafID : leavesID) {
				Set<Integer> returnedEdges = calculator.getReacheableEdgesFrom(leafID);
				Set<Integer> expectedEdges = new HashSet<>();
				AllDirectedPaths<Integer, Integer> pathFinder = new AllDirectedPaths<>(calculator.getSparseGraph());
				List<GraphPath<Integer, Integer>> paths = 
						pathFinder.getAllPaths(calculator.indexOf(leafID), calculator.indexOf(rootID), true, 999);
				for (GraphPath<Integer, Integer> path : paths)
					expectedEdges.addAll(path.getEdgeList());
				if (!returnedEdges.equals(expectedEdges))
					asExpected = false;
				nbOfChecks++;
			}
		}
		assertTrue(asExpected && nbOfChecks > 0);
	}
	
	private List<List<ICategory>> buildCategoryPowerSet(List<ICategory> categorySet) {
	    List<List<ICategory>> powerSet = new ArrayList<>();
	    for (int i = 0; i < (1 << categorySet.size()); i++) {
	    	List<ICategory> subset = new ArrayList<>();
	        for (int j = 0; j < categorySet.size(); j++) {
	            if(((1 << j) & i) > 0)
	            	subset.add(categorySet.get(j));
	        }
	        powerSet.add(subset);
	    }
	    return powerSet;
	}

}
