package com.tregouet.occam.alg.setters.weighs.categorization_transitions;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.setters.weighs.Weigher;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;

public interface ProblemTransitionWeigher extends Weigher<AProblemStateTransition> {

	ProblemTransitionWeigher setContext(
			DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph);

}
