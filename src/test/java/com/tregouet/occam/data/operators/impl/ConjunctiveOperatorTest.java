package com.tregouet.occam.data.operators.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.cost_calculation.PropertyWeighingStrategy;
import com.tregouet.occam.cost_calculation.SimilarityCalculationStrategy;
import com.tregouet.occam.data.concepts.IClassificationTreeSupplier;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.concepts.impl.Concepts;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IConjunctiveOperator;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.transition_function.ITransitionFunction;
import com.tregouet.occam.transition_function.impl.TransitionFunction;
import com.tregouet.occam.transition_function.impl.TransitionFunctionSupplier;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

public class ConjunctiveOperatorTest {
	
	private static final Path SHAPES1 = Paths.get(".", "src", "test", "java", "files", "shapes1bis.txt");
	private static final PropertyWeighingStrategy PROP_WHEIGHING_STRATEGY = 
			PropertyWeighingStrategy.INFORMATIVITY_DIAGNOSTIVITY;
	private static final SimilarityCalculationStrategy SIM_CALC_STRATEGY = 
			SimilarityCalculationStrategy.CONTRAST_MODEL;
	private static List<IContextObject> shapes1Obj;
	private IConcepts concepts;
	private DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IClassificationTreeSupplier classificationTreeSupplier;
	private Tree<IConcept, IsA> catTree;
	private DirectedAcyclicGraph<IIntentAttribute, IProduction> filtered_constructs;
	private IHierarchicalRestrictionFinder<IIntentAttribute, IProduction> constrTreeSupplier;
	private Tree<IIntentAttribute, IProduction> constrTree;
	private TreeSet<ITransitionFunction> transitionFunctions = new TreeSet<>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes1Obj = GenericFileReader.getContextObjects(SHAPES1);
	}

	@Before
	public void setUp() throws Exception {
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
			filtered_constructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByCategoryTree(catTree, constructs);
			constrTreeSupplier = new RestrictorOpt<>(filtered_constructs, true);
			while (constrTreeSupplier.hasNext()) {
				constrTree = constrTreeSupplier.next();
				ITransitionFunction transitionFunction = 
						new TransitionFunction(shapes1Obj, concepts.getSingletonConcept(), catTree, constrTree, 
								PROP_WHEIGHING_STRATEGY, SIM_CALC_STRATEGY);
				transitionFunctions.add(transitionFunction);
			}
		}	
	}

	@Test
	public void whenOperatorAdditionRequestedThenReturnsTrueOnlyIfSameStateTransition() {
		boolean ifTrueThenSameStateTransition = true;
		int nbOfChecks = 0;
		for (ITransitionFunction tF : transitionFunctions) {
			List<IOperator> operators = tF.getTransitions();
			List<IConjunctiveOperator> conjunctiveOperators = new ArrayList<>();
			for (IOperator op : operators) {
				boolean match = false;
				for (IConjunctiveOperator conOp : conjunctiveOperators) {
					if (conOp.addOperator(op)) {
						match = true;
						if (!conOp.getOperatingState().equals(op.getOperatingState())
								|| !conOp.getNextState().equals(op.getNextState())) {
							ifTrueThenSameStateTransition = false;
						}
						nbOfChecks++;
					}
				}
				if (!match) {
					conjunctiveOperators.add(new ConjunctiveOperator(op));
				}
			}
		}
		assertTrue(nbOfChecks > 0 && ifTrueThenSameStateTransition);
	}
	
	@Test
	public void whenOperatorAdditionRequestedThenReturnsFalseOnlyIfDifferentStateTransition() {
		boolean ifFalseThenDifferentStateTransition = true;
		int nbOfChecks = 0;
		for (ITransitionFunction tF : transitionFunctions) {
			List<IOperator> operators = tF.getTransitions();
			List<IConjunctiveOperator> conjunctiveOperators = new ArrayList<>();
			for (IOperator op : operators) {
				boolean noMatch = true;
				for (IConjunctiveOperator conOp : conjunctiveOperators) {
					if (!conOp.addOperator(op)) {
						if (conOp.getOperatingState().equals(op.getOperatingState())
								&& conOp.getNextState().equals(op.getNextState())) {
							ifFalseThenDifferentStateTransition = false;
						}
						nbOfChecks++;
					}
					else noMatch = false;
				}
				if (noMatch)
					conjunctiveOperators.add(new ConjunctiveOperator(op));
			}
		}
		assertTrue(nbOfChecks > 0 && ifFalseThenDifferentStateTransition);
	}

}
