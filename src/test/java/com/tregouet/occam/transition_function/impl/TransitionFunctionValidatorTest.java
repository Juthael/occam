package com.tregouet.occam.transition_function.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.cost_calculation.SimilarityCalculationStrategy;
import com.tregouet.occam.data.concepts.IClassificationTreeSupplier;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.concepts.impl.Concepts;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.data.operators.impl.ProductionBuilder;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.io.output.utils.Visualizer;
import com.tregouet.occam.transition_function.ITransitionFunction;
import com.tregouet.occam.transition_function.TransitionFunctionGraphType;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

@SuppressWarnings("unused")
public class TransitionFunctionValidatorTest {

	private static final Path SHAPES2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static final SimilarityCalculationStrategy SIM_CALC_STRATEGY = 
			SimilarityCalculationStrategy.CONTRAST_MODEL;
	private static List<IContextObject> shapes1Obj;
	private IConcepts concepts;
	private DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IClassificationTreeSupplier classificationTreeSupplier;
	private Tree<IConcept, IsA> catTree;
	private DirectedAcyclicGraph<IIntentAttribute, IProduction> filtered_reduced_constructs;
	private IHierarchicalRestrictionFinder<IIntentAttribute, IProduction> constrTreeSupplier;
	private Tree<IIntentAttribute, IProduction> constrTree;
	private TreeSet<ITransitionFunction> transitionFunctions;	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes1Obj = GenericFileReader.getContextObjects(SHAPES2);	
	}

	@Before
	public void setUp() throws Exception {
		transitionFunctions = new TreeSet<>();
		concepts = new Concepts(shapes1Obj);
		List<IProduction> productions = new ProductionBuilder(concepts).getProductions();
		productions.stream().forEach(p -> {
			constructs.addVertex(p.getSource());
			constructs.addVertex(p.getTarget());
			constructs.addEdge(p.getSource(), p.getTarget(), p);
		});
		classificationTreeSupplier = concepts.getCatTreeSupplier();
		while (classificationTreeSupplier.hasNext()) {
			catTree = classificationTreeSupplier.nextOntologicalCommitment();
			filtered_reduced_constructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByCategoryTree(catTree, constructs);
			constrTreeSupplier = new RestrictorOpt<>(filtered_reduced_constructs, true);
			while (constrTreeSupplier.hasNext()) {
				constrTree = constrTreeSupplier.nextTransitiveReduction();
				ITransitionFunction transitionFunction = 
						new TransitionFunction(shapes1Obj, concepts.getSingletonConcept(), catTree, constrTree, 
								SIM_CALC_STRATEGY);
				transitionFunctions.add(transitionFunction);
			}
		}
	}

	@Test
	public void onlyWhenTransitsThroughEveryStateThenTransitionFunctionIsValid() throws IOException {
		boolean asExpected = true;
		Set<ITransitionFunction> validTransitionFunctions = new HashSet<>();
		Set<ITransitionFunction> invalidTransitionFunctions = new HashSet<>();
		for (ITransitionFunction tF : transitionFunctions) {
			if (tF.validate(TransitionFunctionValidator.INSTANCE))
				validTransitionFunctions.add(tF);
			else invalidTransitionFunctions.add(tF);
		}
		for (ITransitionFunction valid : validTransitionFunctions) {
			if (containsSomeUnreacheableState(valid)) {
				/*
				Visualizer.visualizeTransitionFunction(valid, "valid", TransitionFunctionGraphType.FINITE_AUTOMATON);
				*/
				asExpected = false;
			}
				
		}
		for (ITransitionFunction invalid : invalidTransitionFunctions) {
			if (!containsSomeUnreacheableState(invalid)) {
				/*
				Visualizer.visualizeTransitionFunction(invalid, "invalid", TransitionFunctionGraphType.FINITE_AUTOMATON);
				*/
				asExpected = false;
			}
		}
		assertTrue(asExpected && !validTransitionFunctions.isEmpty() && !invalidTransitionFunctions.isEmpty());
	}
	
	private boolean containsSomeUnreacheableState(ITransitionFunction tF) {
		return !new ConnectivityInspector<>(tF.getFiniteAutomatonGraph()).isConnected();
	}

}
