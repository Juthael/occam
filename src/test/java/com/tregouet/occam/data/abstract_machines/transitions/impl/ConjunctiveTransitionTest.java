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

import com.tregouet.occam.alg.denotation_sets_gen.IDenotationSetsTreeSupplier;
import com.tregouet.occam.alg.scoring.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring.ScoringStrategy;
import com.tregouet.occam.alg.transition_function_gen.impl.ProductionBuilder;
import com.tregouet.occam.alg.transition_function_gen.impl.TransitionFunctionSupplier;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.impl.TransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.utils.ScoreThenCostTFComparator;
import com.tregouet.occam.data.abstract_machines.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.abstract_machines.transitions.ICostedTransition;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.abstract_machines.transitions.ITransition;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IDenotationSets;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.occam.data.denotations.impl.DenotationSets;
import com.tregouet.occam.data.languages.generic.IContextObject;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

public class ConjunctiveTransitionTest {
	
	private static final Path SHAPES1 = Paths.get(".", "src", "test", "java", "files", "shapes1bis.txt");
	private static List<IContextObject> shapes1Obj;
	private IDenotationSets denotationSets;
	private DirectedAcyclicGraph<IDenotation, IProduction> denotations = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IDenotationSetsTreeSupplier denotationSetsTreeSupplier;
	private Tree<IDenotationSet, IIsA> treeOfDenotationSets;
	private DirectedAcyclicGraph<IDenotation, IProduction> filtered_denotations;
	private IHierarchicalRestrictionFinder<IDenotation, IProduction> denotationTreeSupplier;
	private Tree<IDenotation, IProduction> denotationTree;
	private TreeSet<ITransitionFunction> transitionFunctions = new TreeSet<>(ScoreThenCostTFComparator.INSTANCE);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes1Obj = GenericFileReader.getContextObjects(SHAPES1);
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy.SCORING_STRATEGY_1);
	}

	@Before
	public void setUp() throws Exception {
		denotationSets = new DenotationSets(shapes1Obj);
		List<IProduction> productions = new ProductionBuilder(denotationSets).getProductions();
		productions.stream().forEach(p -> {
			denotations.addVertex(p.getSource());
			denotations.addVertex(p.getTarget());
			denotations.addEdge(p.getSource(), p.getTarget(), p);
		});
		denotationSetsTreeSupplier = denotationSets.getDenotationSetsTreeSupplier();
		while (denotationSetsTreeSupplier.hasNext()) {
			treeOfDenotationSets = denotationSetsTreeSupplier.next();
			filtered_denotations = 
					TransitionFunctionSupplier.getDenotationGraphFilteredByTreeOfDenotationSets(treeOfDenotationSets, denotations);
			denotationTreeSupplier = new RestrictorOpt<>(filtered_denotations, true);
			while (denotationTreeSupplier.hasNext()) {
				denotationTree = denotationTreeSupplier.next();
				ITransitionFunction transitionFunction = 
						new TransitionFunction(treeOfDenotationSets, denotationTree);
				transitionFunctions.add(transitionFunction);
			}
		}	
	}

	@Test
	public void whenOperatorAdditionRequestedThenReturnsFalseOnlyIfDifferentStateTransition() {
		boolean ifFalseThenDifferentStateTransition = true;
		int nbOfChecks = 0;
		for (ITransitionFunction tF : transitionFunctions) {
			List<ICostedTransition> operators = tF.getTransitions();
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
	
	@Test
	public void whenOperatorAdditionRequestedThenReturnsTrueOnlyIfSameStateTransition() {
		boolean ifTrueThenSameStateTransition = true;
		int nbOfChecks = 0;
		for (ITransitionFunction tF : transitionFunctions) {
			List<ICostedTransition> operators = tF.getTransitions();
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

}
