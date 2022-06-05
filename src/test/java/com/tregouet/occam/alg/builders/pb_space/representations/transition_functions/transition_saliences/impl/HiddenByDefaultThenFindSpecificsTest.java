package com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.transition_saliences.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.alg.builders.pb_space.utils.MapConceptIDs2ExtentIDs;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.classifications.impl.Classification;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.states.productions.IProduction;
import com.tregouet.occam.data.problem_space.states.productions.Salience;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.problem_space.states.transitions.TransitionType;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.InvertedTree;

public class HiddenByDefaultThenFindSpecificsTest {
	
	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	@SuppressWarnings("unused")
	private static final String nL = System.lineSeparator();
	private List<IContextObject> context;
	private IConceptLattice conceptLattice;	
	private Set<IContextualizedProduction> productions;
	private Set<InvertedTree<IConcept, IIsA>> trees;
	private Set<IRepresentationTransitionFunction> transFunctions = new HashSet<>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OverallAbstractFactory.INSTANCE.apply(Occam.strategy);
	}

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);
		conceptLattice = BuildersAbstractFactory.INSTANCE.getConceptLatticeBuilder().apply(context);
		productions = BuildersAbstractFactory.INSTANCE.getProdBuilderFromConceptLattice().apply(conceptLattice);
		trees = growTrees();
		RepresentationTransFuncBuilder transFuncBldr;
		for (InvertedTree<IConcept, IIsA> tree : trees) {
			Map<Integer, List<Integer>> conceptID2ExtentIDs = MapConceptIDs2ExtentIDs.in(tree);
			IClassification classification = new Classification(tree, conceptID2ExtentIDs);	
			transFuncBldr = BuildersAbstractFactory.INSTANCE.getRepresentationTransFuncBuilder();
			transFunctions.add(transFuncBldr.apply(classification, productions));
		}
	}

	@Test
	public void ifTransitionNotAnApplicationThenHidden() {
		boolean asExpected = true;
		int nbOfChecks = 0;
		for (IRepresentationTransitionFunction transFunc : transFunctions) {
			for (IConceptTransition transition : transFunc.getTransitions()) {
				if(transition.type() != TransitionType.APPLICATION) {
					nbOfChecks++;
					if (transition.getSalience() != Salience.HIDDEN)
						asExpected = false;
				}
			}
		}
		assertTrue(nbOfChecks > 0 && asExpected);
	}
	
	@Test
	public void ifTransitionIsHiddenApplicationThenOutputStateIsParticular() {
		boolean asExpected = true;
		int nbOfChecks = 0;
		for (IRepresentationTransitionFunction transFunc : transFunctions) {
			Set<Integer> particularIDs = transFunc.getAcceptStateIDs();
			for (IConceptTransition transition : transFunc.getTransitions()) {
				if (transition.type() == TransitionType.APPLICATION && transition.getSalience() == Salience.HIDDEN) {
					nbOfChecks++;
					if (!particularIDs.contains(transition.getOutputInternConfiguration().getOutputStateID()))
						asExpected = false;
				}
			}
		}
		assertTrue(nbOfChecks > 0 && asExpected);
	}	
	
	@Test
	public void ifTransitionIsRuleThenRulesTowardsConcurrentOutputsCanBeFound() {
		boolean asExpected = true;
		int nbOfChecks = 0;
		for (IRepresentationTransitionFunction transFunc : transFunctions) {
			for (IConceptTransition transition : transFunc.getTransitions()) {
				if (transition.getSalience() == Salience.TRANSITION_RULE) {
					nbOfChecks++;
					IConceptTransitionIC inputConfig = transition.getInputConfiguration();
					List<IConceptTransition> rules = 
							findApplicationsWithSpecifiedInputStateAndStackSymbol(
									transFunc, inputConfig.getInputStateID(), inputConfig.getStackSymbol());
					Set<IProduction> values = new HashSet<>();
					for (IConceptTransition rule : rules) {
						if (rule.getSalience() != Salience.TRANSITION_RULE)
							asExpected = false;
						values.add(rule.getInputConfiguration().getInputSymbol());
					}
					if (values.size() != rules.size())
						asExpected = false;
				}
			}
		}
		assertTrue(nbOfChecks > 0 && asExpected);
	}
	
	private List<IConceptTransition> findApplicationsWithSpecifiedInputStateAndStackSymbol(
			IRepresentationTransitionFunction transFunc, int inputStateID, AVariable stackSymbol) {
		List<IConceptTransition> assumedRules = new ArrayList<>();
		for (IConceptTransition transition : transFunc.getTransitions()) {
			if (transition.type() == TransitionType.APPLICATION) {
				IConceptTransitionIC inputConfig = transition.getInputConfiguration();
				if (inputConfig.getInputStateID() == inputStateID && inputConfig.getStackSymbol().equals(stackSymbol))
					assumedRules.add(transition);
			}
		}
		return assumedRules;
	}
	
	@Test
	public void ifTransitionIsCommonFeatureThenOutputStateIsNotParticular() {
		boolean asExpected = true;
		int nbOfChecks = 0;
		for (IRepresentationTransitionFunction transFunc : transFunctions) {
			Set<Integer> particularIDs = new HashSet<>();
			for (IConcept particular : conceptLattice.getOntologicalUpperSemilattice().getLeaves())
				particularIDs.add(particular.iD());
			for (IConceptTransition transition : transFunc.getTransitions()) {
				if (transition.getSalience() == Salience.COMMON_FEATURE) {
					nbOfChecks++;
					if (particularIDs.contains(transition.getOutputInternConfiguration().getOutputStateID()))
						asExpected = false;
				}
			}
		}
		assertTrue(nbOfChecks > 0 && asExpected);
	}
	
	private Set<InvertedTree<IConcept, IIsA>> growTrees() {
		Set<InvertedTree<IConcept, IIsA>> expandedTrees = new HashSet<>();
		Set<InvertedTree<IConcept, IIsA>> expandedTreesFromLastIteration;
		expandedTreesFromLastIteration = BuildersAbstractFactory.INSTANCE.getConceptTreeGrower().apply(conceptLattice, null);
		do {
			expandedTrees.addAll(expandedTreesFromLastIteration);
			Set<InvertedTree<IConcept, IIsA>> expandable = new HashSet<>(expandedTreesFromLastIteration);
			expandedTreesFromLastIteration.clear();
			for (InvertedTree<IConcept, IIsA> tree : expandable) {
				expandedTreesFromLastIteration.addAll(
						BuildersAbstractFactory.INSTANCE.getConceptTreeGrower().apply(conceptLattice, tree)); 
			}
		}
		while (!expandedTreesFromLastIteration.isEmpty());
		return expandedTrees;
	}		

}
