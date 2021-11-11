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

import com.tregouet.occam.cost_calculation.PropertyWeighingStrategy;
import com.tregouet.occam.cost_calculation.SimilarityCalculationStrategy;
import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IClassificationTreeSupplier;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.categories.impl.Categories;
import com.tregouet.occam.data.categories.impl.IsA;
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

public class TransitionFunctionValidatorTest {

	private static final Path SHAPES2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static final PropertyWeighingStrategy PROP_WHEIGHING_STRATEGY = 
			PropertyWeighingStrategy.INFORMATIVITY_DIAGNOSTIVITY;
	private static final SimilarityCalculationStrategy SIM_CALC_STRATEGY = 
			SimilarityCalculationStrategy.CONTRAST_MODEL;
	private static List<IContextObject> shapes1Obj;
	private ICategories categories;
	private DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IClassificationTreeSupplier classificationTreeSupplier;
	private Tree<ICategory, IsA> catTree;
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
		categories = new Categories(shapes1Obj);
		List<IProduction> productions = new ProductionBuilder(categories).getProductions();
		productions.stream().forEach(p -> {
			constructs.addVertex(p.getSource());
			constructs.addVertex(p.getTarget());
			constructs.addEdge(p.getSource(), p.getTarget(), p);
		});
		classificationTreeSupplier = categories.getCatTreeSupplier();
		while (classificationTreeSupplier.hasNext()) {
			catTree = classificationTreeSupplier.nextOntologicalCommitment();
			filtered_reduced_constructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByCategoryTree(catTree, constructs);
			constrTreeSupplier = new RestrictorOpt<>(filtered_reduced_constructs, true);
			while (constrTreeSupplier.hasNext()) {
				constrTree = constrTreeSupplier.nextTransitiveReduction();
				ITransitionFunction transitionFunction = 
						new TransitionFunction(shapes1Obj, categories.getObjectCategories(), catTree, constrTree, 
								PROP_WHEIGHING_STRATEGY, SIM_CALC_STRATEGY);
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