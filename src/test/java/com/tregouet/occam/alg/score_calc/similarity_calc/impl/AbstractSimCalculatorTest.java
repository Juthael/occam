package com.tregouet.occam.alg.score_calc.similarity_calc.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.alg.conceptual_structure_gen.IClassificationSupplier;
import com.tregouet.occam.alg.score_calc.CalculatorFactory;
import com.tregouet.occam.alg.score_calc.OverallScoringStrategy;
import com.tregouet.occam.alg.score_calc.similarity_calc.SimilarityCalculationStrategy;
import com.tregouet.occam.alg.transition_function_gen.impl.ProductionBuilder;
import com.tregouet.occam.alg.transition_function_gen.impl.TransitionFunctionSupplier;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.impl.TransitionFunction;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.concepts.IClassification;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.concepts.impl.Concepts;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.occam.data.languages.generic.IContextObject;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

public class AbstractSimCalculatorTest {

	private static final Path shapes2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;
	private IConcepts concepts;
	private DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IClassificationSupplier classificationSupplier;
	private DirectedAcyclicGraph<IIntentAttribute, IProduction> filtered_reduced_constructs;
	private IHierarchicalRestrictionFinder<IIntentAttribute, IProduction> constrTreeSupplier;
	private Tree<IIntentAttribute, IProduction> constrTree;
	private TreeSet<ITransitionFunction> transitionFunctions = new TreeSet<>();
	
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
			Tree<IConcept, IsA> currConceptTree = currClassification.getClassificationTree();
			filtered_reduced_constructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByCategoryTree(currConceptTree, constructs);
			constrTreeSupplier = new RestrictorOpt<>(filtered_reduced_constructs, true);
			while (constrTreeSupplier.hasNext()) {
				constrTree = constrTreeSupplier.next();
				ITransitionFunction transitionFunction = 
						new TransitionFunction(currClassification, constrTree);
				transitionFunctions.add(transitionFunction);
				/*
				Visualizer.visualizeTransitionFunction(transitionFunction, "2109110911_tf", 
						TransitionFunctionGraphType.FINITE_AUTOMATON);
				Visualizer.visualizeWeightedTransitionsGraph(transitionFunction.getSimilarityCalculator().getSparseGraph(), "2109110911_sg");
				*/
			}
		}
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
			ContrastModel calculator = 
					(ContrastModel) SimilarityCalculatorFactory.INSTANCE.apply(
							SimilarityCalculationStrategy.CONTRAST_MODEL).input(tF.getClassification());
			for (Integer leafID : leavesID) {
				Set<Integer> returnedEdges = new HashSet<>(calculator.getEdgeChainToRootFrom(leafID.intValue()));
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

}
