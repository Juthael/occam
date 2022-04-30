package com.tregouet.occam.alg.builders.problem_spaces.utils;

import java.util.Collection;
import java.util.Set;
import java.util.function.BiFunction;

import org.jgrapht.Graphs;
import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.problem_spaces.ProblemSpaceBuilder;
import com.tregouet.occam.alg.builders.problem_spaces.ranker.ProblemTransitionRanker;
import com.tregouet.occam.alg.setters.weighs.categorization_transitions.ProblemTransitionWeigher;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.IProblemState;

public class ProblemSpaceGraphInstantiator implements BiFunction<
	Collection<IProblemState>, 
	Set<AProblemStateTransition>, 
	DirectedAcyclicGraph<IProblemState, AProblemStateTransition>> {
	
	public static final ProblemSpaceGraphInstantiator INSTANCE = new ProblemSpaceGraphInstantiator();
	
	private ProblemSpaceGraphInstantiator() {
	}

	@Override
	public DirectedAcyclicGraph<IProblemState, AProblemStateTransition> apply(Collection<IProblemState> states,
			Set<AProblemStateTransition> transitions) {
		DirectedAcyclicGraph<IProblemState, AProblemStateTransition> problemGraph = new DirectedAcyclicGraph<>(null,
				null, true);
		Graphs.addAllVertices(problemGraph, states);
		for (AProblemStateTransition transition : transitions)
			problemGraph.addEdge(transition.getSource(), transition.getTarget(), transition);
		TransitiveReduction.INSTANCE.reduce(problemGraph);
		ProblemTransitionRanker ranker = ProblemSpaceBuilder.getProblemTransitionRanker().setUp(problemGraph);
		ProblemTransitionWeigher weigher = ProblemSpaceBuilder.getProblemTransitionWeigher()
				.setContext(problemGraph);
		for (AProblemStateTransition transition : problemGraph.edgeSet()) {
			ranker.accept(transition);
			weigher.accept(transition);
		}
		return problemGraph;
	}
	
	

}
