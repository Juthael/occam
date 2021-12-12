package com.tregouet.occam.dep.cost_calculation.similarity_calculation.impl;

import static org.junit.Assert.*;

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
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.alg.conceptual_structure_gen.IClassificationSupplier;
import com.tregouet.occam.alg.score_calc.similarity_calc.ISimilarityCalculator;
import com.tregouet.occam.alg.score_calc.similarity_calc.SimilarityCalculationStrategy;
import com.tregouet.occam.alg.score_calc.similarity_calc.impl.ContrastModelDep;
import com.tregouet.occam.alg.transition_function_gen.impl.ProductionBuilder;
import com.tregouet.occam.alg.transition_function_gen.impl.TransitionFunctionSupplier;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.TransitionFunctionGraphType;
import com.tregouet.occam.data.abstract_machines.functions.impl.TransitionFunction;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.concepts.impl.Concepts;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.occam.data.languages.generic.IContextObject;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.io.output.utils.Visualizer;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

@SuppressWarnings("unused")
public class ContrastModelTest {

	private static final Path shapes2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static final SimilarityCalculationStrategy SIM_CALCULATION_STRATEGY = 
			SimilarityCalculationStrategy.CONTRAST_MODEL;
	private static List<IContextObject> shapes2Obj;
	private IConcepts concepts;
	private DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IClassificationSupplier classificationSupplier;
	private Tree<IConcept, IsA> catTree;
	private DirectedAcyclicGraph<IIntentAttribute, IProduction> filtered_reduced_constructs;
	private IHierarchicalRestrictionFinder<IIntentAttribute, IProduction> constrTreeSupplier;
	private Tree<IIntentAttribute, IProduction> constrTree;
	private TreeSet<ITransitionFunction> transitionFunctions = new TreeSet<>();
	private Map<ITransitionFunction, ISimilarityCalculator> tfToSimCalc = new HashMap<>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(shapes2);
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
		classificationSupplier = concepts.getCatTreeSupplier();
		while (classificationSupplier.hasNext()) {
			catTree = classificationSupplier.nextOntologicalCommitment();
			filtered_reduced_constructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByCategoryTree(catTree, constructs);
			constrTreeSupplier = new RestrictorOpt<>(filtered_reduced_constructs, true);
			while (constrTreeSupplier.hasNext()) {
				constrTree = constrTreeSupplier.next();
				ITransitionFunction transitionFunction = 
						new TransitionFunction(shapes2Obj, concepts.getSingletonConcept(), catTree, constrTree, 
								SIM_CALCULATION_STRATEGY);
				transitionFunctions.add(transitionFunction);
				/*
				Visualizer.visualizeTransitionFunction(transitionFunction, "2109110911_tf", 
						TransitionFunctionGraphType.FINITE_AUTOMATON);
				Visualizer.visualizeWeightedTransitionsGraph(transitionFunction.getSimilarityCalculator().getSparseGraph(), "2109110911_sg");
				*/
				tfToSimCalc.put(transitionFunction, transitionFunction.getSimilarityCalculator());
			}
		}
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
			ContrastModelDep calculator = (ContrastModelDep) tF.getSimilarityCalculator();
			/*
			System.out.println("***NEW TRANSITION FUNCTION***" + System.lineSeparator());
			Visualizer.visualizeTransitionFunction(tF, "2109161427_tf", TransitionFunctionGraphType.FINITE_AUTOMATON);
			Visualizer.visualizeWeightedTransitionsGraph(calculator.getSparseGraph(), "2109161427_sg");
			*/
			List<IConcept> objects = new ArrayList<>(tF.getCategoryTree().getLeaves());
			int[] objectIDs = new int[objects.size()];
			for (int i = 0 ; i < objectIDs.length ; i++) {
				objectIDs[i] = objects.get(i).getID();
			}
			for (int j = 0 ; j < objectIDs.length - 1 ; j++) {
				for (int k = j+1 ; k < objectIDs.length ; k++) {
					double similarity = calculator.howSimilar(objectIDs[j], objectIDs[k]);
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
			ContrastModelDep calculator = (ContrastModelDep) tF.getSimilarityCalculator();
			/*
			System.out.println("***NEW TRANSITION FUNCTION***" + System.lineSeparator());
			Visualizer.visualizeTransitionFunction(tF, "2109161427_tf", TransitionFunctionGraphType.FINITE_AUTOMATON);
			Visualizer.visualizeWeightedTransitionsGraph(calculator.getSparseGraph(), "2109161427_sg");
			*/
			List<IConcept> objects = new ArrayList<>(tF.getCategoryTree().getLeaves());
			int[] objectIDs = new int[objects.size()];
			for (int i = 0 ; i < objectIDs.length ; i++) {
				objectIDs[i] = objects.get(i).getID();
			}
			for (int j = 0 ; j < objectIDs.length ; j++) {
				for (int k = 0 ; k < objectIDs.length ; k++) {
					double similarity = calculator.howSimilarTo(objectIDs[j], objectIDs[k]);
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
	public void whenPrototypicalityOfAnObjectRequestedThenReturned() throws IOException {
		boolean returned = true;
		int nbOfChecks = 0;
		for (ITransitionFunction tF : transitionFunctions) {
			ContrastModelDep calculator = (ContrastModelDep) tF.getSimilarityCalculator();
			/*
			System.out.println("***NEW TRANSITION FUNCTION***" + System.lineSeparator());
			Visualizer.visualizeTransitionFunction(tF, "2109161427_tf", TransitionFunctionGraphType.FINITE_AUTOMATON);
			Visualizer.visualizeWeightedTransitionsGraph(calculator.getSparseGraph(), "2109161427_sg");
			*/
			List<IConcept> objects = new ArrayList<>(tF.getCategoryTree().getLeaves());
			int[] objectIDs = new int[objects.size()];
			for (int i = 0 ; i < objectIDs.length ; i++) {
				objectIDs[i] = objects.get(i).getID();				
			}
			for (int j = 0 ; j < objectIDs.length ; j++) {
				double prototypicality = calculator.howProtoypical(objectIDs[j]);
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
			List<IConcept> categorySet = tF.getCategoryTree().getTopologicalOrder();
			List<List<IConcept>> categoryPowerSet = buildCategoryPowerSet(categorySet);
			for (IConcept cat : categorySet) {
				for (List<IConcept> subset : categoryPowerSet) {
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
			List<IConcept> leaves = new ArrayList<>(tF.getCategoryTree().getLeaves());
			int[] leavesID = new int[leaves.size()];
			for (int i = 0 ; i < leavesID.length ; i++) {
				leavesID[i] = leaves.get(i).getID();
			}
			ContrastModelDep calculator = (ContrastModelDep) tfToSimCalc.get(tF);
			for (Integer leafID : leavesID) {
				Set<Integer> returnedEdges = calculator.getReacheableEdgesFrom(leafID.intValue());
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
	
	private List<List<IConcept>> buildCategoryPowerSet(List<IConcept> categorySet) {
	    List<List<IConcept>> powerSet = new ArrayList<>();
	    for (int i = 0; i < (1 << categorySet.size()); i++) {
	    	List<IConcept> subset = new ArrayList<>();
	        for (int j = 0; j < categorySet.size(); j++) {
	            if(((1 << j) & i) > 0)
	            	subset.add(categorySet.get(j));
	        }
	        powerSet.add(subset);
	    }
	    return powerSet;
	}

}
