package com.tregouet.occam.data.abstract_machines.transitions.impl;

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

import com.tregouet.occam.alg.conceptual_structure_gen.IClassificationSupplier;
import com.tregouet.occam.alg.score_calc.similarity_calc.SimilarityCalculationStrategy;
import com.tregouet.occam.alg.transition_function_gen.impl.ProductionBuilder;
import com.tregouet.occam.alg.transition_function_gen.impl.TransitionFunctionSupplier;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.impl.TransitionFunction;
import com.tregouet.occam.data.abstract_machines.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.abstract_machines.transitions.ITransition;
import com.tregouet.occam.data.abstract_machines.transitions.impl.ConjunctiveTransition;
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

public class ConjunctiveTransitionTest {
	
	private static final Path SHAPES1 = Paths.get(".", "src", "test", "java", "files", "shapes1bis.txt");
	private static List<IContextObject> shapes1Obj;
	private IConcepts concepts;
	private DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IClassificationSupplier classificationSupplier;
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
		classificationSupplier = concepts.getClassificationSupplier();
		while (classificationSupplier.hasNext()) {
			IClassification currClassification = classificationSupplier.next();
			catTree = currClassification.getClassificationTree();
			filtered_constructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByCategoryTree(catTree, constructs);
			constrTreeSupplier = new RestrictorOpt<>(filtered_constructs, true);
			while (constrTreeSupplier.hasNext()) {
				constrTree = constrTreeSupplier.next();
				ITransitionFunction transitionFunction = 
						new TransitionFunction(currClassification, constrTree);
				transitionFunctions.add(transitionFunction);
			}
		}	
	}

	@Test
	public void whenOperatorAdditionRequestedThenReturnsTrueOnlyIfSameStateTransition() {
		boolean ifTrueThenSameStateTransition = true;
		int nbOfChecks = 0;
		for (ITransitionFunction tF : transitionFunctions) {
			List<ITransition> operators = tF.getTransitions();
			List<IConjunctiveTransition> conjunctiveTransitions = new ArrayList<>();
			for (ITransition op : operators) {
				boolean match = false;
				for (IConjunctiveTransition conOp : conjunctiveTransitions) {
					if (conOp.addTransition(op)) {
						match = true;
						if (!conOp.getOperatingState().equals(op.getOperatingState())
								|| !conOp.getNextState().equals(op.getNextState())) {
							ifTrueThenSameStateTransition = false;
						}
						nbOfChecks++;
					}
				}
				if (!match) {
					conjunctiveTransitions.add(new ConjunctiveTransition(op));
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
			List<ITransition> operators = tF.getTransitions();
			List<IConjunctiveTransition> conjunctiveTransitions = new ArrayList<>();
			for (ITransition op : operators) {
				boolean noMatch = true;
				for (IConjunctiveTransition conOp : conjunctiveTransitions) {
					if (!conOp.addTransition(op)) {
						if (conOp.getOperatingState().equals(op.getOperatingState())
								&& conOp.getNextState().equals(op.getNextState())) {
							ifFalseThenDifferentStateTransition = false;
						}
						nbOfChecks++;
					}
					else noMatch = false;
				}
				if (noMatch)
					conjunctiveTransitions.add(new ConjunctiveTransition(op));
			}
		}
		assertTrue(nbOfChecks > 0 && ifFalseThenDifferentStateTransition);
	}

}
