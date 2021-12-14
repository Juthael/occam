package com.tregouet.occam.alg.score_calc.similarity_calc.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.alg.conceptual_structure_gen.IClassificationSupplier;
import com.tregouet.occam.alg.score_calc.CalculatorFactory;
import com.tregouet.occam.alg.score_calc.OverallScoringStrategy;
import com.tregouet.occam.alg.score_calc.similarity_calc.ISimilarityCalculator;
import com.tregouet.occam.alg.score_calc.similarity_calc.SimilarityCalculationStrategy;
import com.tregouet.occam.alg.transition_function_gen.impl.ProductionBuilder;
import com.tregouet.occam.alg.transition_function_gen.impl.TransitionFunctionSupplier;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.impl.TransitionFunction;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.concepts.IClassification;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IIntentConstruct;
import com.tregouet.occam.data.concepts.impl.Concepts;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.occam.data.languages.generic.IContextObject;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

public class RatioModelTest {

	private static final Path shapes2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;
	private IConcepts concepts;
	private DirectedAcyclicGraph<IIntentConstruct, IProduction> constructs = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IClassificationSupplier classificationSupplier;
	private Tree<IConcept, IsA> catTree;
	private DirectedAcyclicGraph<IIntentConstruct, IProduction> filtered_reduced_constructs;
	private IHierarchicalRestrictionFinder<IIntentConstruct, IProduction> constrTreeSupplier;
	private Tree<IIntentConstruct, IProduction> constrTree;
	private TreeSet<ITransitionFunction> transitionFunctions = new TreeSet<>();
	private Map<ITransitionFunction, ISimilarityCalculator> tfToSimCalc = new HashMap<>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(shapes2);
		CalculatorFactory.INSTANCE.setUpStrategy(OverallScoringStrategy.CONCEPTUAL_COHERENCE);
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
		classificationSupplier = concepts.getClassificationSupplier();
		while (classificationSupplier.hasNext()) {
			IClassification currClassification = classificationSupplier.next();
			catTree = currClassification.getClassificationTree();
			filtered_reduced_constructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByCategoryTree(catTree, constructs);
			constrTreeSupplier = new RestrictorOpt<>(filtered_reduced_constructs, true);
			while (constrTreeSupplier.hasNext()) {
				constrTree = constrTreeSupplier.next();
				ITransitionFunction transitionFunction = 
						new TransitionFunction(currClassification, constrTree);
				transitionFunctions.add(transitionFunction);
				/*
				Visualizer.visualizeTransitionFunction(transitionFunction, "2109110911_tf", 
						TransitionFunctionGraphType.FINITE_AUTOMATON);
				Visualizer.visualizeWeightedTransitionsGraph(transitionFunction.getSimilarityCalculator().getSparseGraph(), 
						"2109110911_sg");
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
			if (Double.isNaN(coherenceScore))
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
			RatioModel calculator = (RatioModel) SimilarityCalculatorFactory.INSTANCE.apply(
					SimilarityCalculationStrategy.RATIO_MODEL).input(tF.getClassification());
			/*
			System.out.println("***NEW TRANSITION FUNCTION***" + System.lineSeparator());
			Visualizer.visualizeTransitionFunction(tF, "2109161427_tf", TransitionFunctionGraphType.FINITE_AUTOMATON);
			Visualizer.visualizeWeightedTransitionsGraph(calculator.getSparseGraph(), "2109161427_sg");
			*/
			List<IConcept> objects = new ArrayList<>(tF.getTreeOfConcepts().getLeaves());
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
			RatioModel calculator = (RatioModel) SimilarityCalculatorFactory.INSTANCE.apply(
					SimilarityCalculationStrategy.RATIO_MODEL).input(tF.getClassification());
			/*
			System.out.println("***NEW TRANSITION FUNCTION***" + System.lineSeparator());
			Visualizer.visualizeTransitionFunction(tF, "2109161427_tf", TransitionFunctionGraphType.FINITE_AUTOMATON);
			Visualizer.visualizeWeightedTransitionsGraph(calculator.getSparseGraph(), "2109161427_sg");
			*/
			List<IConcept> objects = new ArrayList<>(tF.getTreeOfConcepts().getLeaves());
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
			RatioModel calculator = (RatioModel) SimilarityCalculatorFactory.INSTANCE.apply(
					SimilarityCalculationStrategy.RATIO_MODEL).input(tF.getClassification());
			/*
			System.out.println("***NEW TRANSITION FUNCTION***" + System.lineSeparator());
			Visualizer.visualizeTransitionFunction(tF, "2109161427_tf", TransitionFunctionGraphType.FINITE_AUTOMATON);
			Visualizer.visualizeWeightedTransitionsGraph(calculator.getSparseGraph(), "2109161427_sg");
			*/
			List<IConcept> objects = new ArrayList<>(tF.getTreeOfConcepts().getLeaves());
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
			List<IConcept> categorySet = tF.getTreeOfConcepts().getTopologicalOrder();
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
