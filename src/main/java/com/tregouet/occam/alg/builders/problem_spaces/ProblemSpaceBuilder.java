package com.tregouet.occam.alg.builders.problem_spaces;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.jgrapht.Graphs;
import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.problem_spaces.partial_representations.PartialRepresentationLateSetter;
import com.tregouet.occam.alg.builders.problem_spaces.transitions.TransitionBuilder;
import com.tregouet.occam.alg.builders.representations.string_pattern.StringPatternBuilder;
import com.tregouet.occam.alg.scorers.ScorersAbstractFactory;
import com.tregouet.occam.alg.scorers.problem_states.ProblemStateScorer;
import com.tregouet.occam.alg.setters.SettersAbstractFactory;
import com.tregouet.occam.alg.setters.weighs.categorization_transitions.CategorizationTransitionWeigher;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.IProblemSpace;
import com.tregouet.occam.data.problem_spaces.IProblemState;
import com.tregouet.occam.data.problem_spaces.impl.ProblemSpace;
import com.tregouet.occam.data.representations.ICompleteRepresentations;

public interface ProblemSpaceBuilder extends Function<ICompleteRepresentations, IProblemSpace> {

	public static IProblemSpace build(List<IProblemState> topoOrderedStates, Set<AProblemStateTransition> transitions) {
		DirectedAcyclicGraph<IProblemState, AProblemStateTransition> problemGraph = new DirectedAcyclicGraph<>(null,
				null, true);
		Graphs.addAllVertices(problemGraph, topoOrderedStates);
		for (AProblemStateTransition transition : transitions)
			problemGraph.addEdge(transition.getSource(), transition.getTarget(), transition);
		TransitiveReduction.INSTANCE.reduce(problemGraph);
		CategorizationTransitionWeigher weigher = ProblemSpaceBuilder.getCategorizationTransitionWeigher()
				.setContext(problemGraph);
		for (AProblemStateTransition transition : problemGraph.edgeSet())
			weigher.accept(transition);
		ProblemStateScorer scorer = ProblemSpaceBuilder.getProblemStateScorer().setUp(problemGraph);
		for (IProblemState state : problemGraph)
			state.setScore(scorer.apply(state));
		PartialRepresentationLateSetter partialRepLateSetter = ProblemSpaceBuilder.getPartialRepresentationLateSetter();
		return new ProblemSpace(problemGraph, partialRepLateSetter);
	}

	public static TransitionBuilder getCategorizationTransitionBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getProblemTransitionBuilder();
	}

	public static CategorizationTransitionWeigher getCategorizationTransitionWeigher() {
		return SettersAbstractFactory.INSTANCE.getCategorizationTransitionWeigher();
	}

	public static PartialRepresentationLateSetter getPartialRepresentationLateSetter() {
		return GeneratorsAbstractFactory.INSTANCE.getPartialRepresentationLateSetter();
	}

	public static ProblemStateScorer getProblemStateScorer() {
		return ScorersAbstractFactory.INSTANCE.getProblemStateScorer();
	}

	public static StringPatternBuilder getStringPatternBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getStringPatternBuilder();
	}

}
