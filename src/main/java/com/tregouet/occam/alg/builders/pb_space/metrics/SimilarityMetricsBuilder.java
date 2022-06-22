package com.tregouet.occam.alg.builders.pb_space.metrics;

import java.util.Set;
import java.util.function.BiFunction;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.problem_space.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;

public interface SimilarityMetricsBuilder 
	extends BiFunction<
		Set<Integer>, 
		DirectedAcyclicGraph<IRepresentation, AProblemStateTransition>, ISimilarityMetrics> {

}
