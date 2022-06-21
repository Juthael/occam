package com.tregouet.occam.alg.builders.pb_space.graph_updater;

import java.util.Set;
import java.util.function.BiFunction;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;

public interface ProblemSpaceGraphUpdater
	extends BiFunction<
		DirectedAcyclicGraph<IRepresentation, AProblemStateTransition>,
		Set<IRepresentation>,
		DirectedAcyclicGraph<IRepresentation, AProblemStateTransition>> {
}
