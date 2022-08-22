package com.tregouet.occam.alg.builders.categorizer.graph_updater;

import java.util.Set;
import java.util.function.BiFunction;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.modules.categorization.transitions.AProblemStateTransition;
import com.tregouet.occam.data.representations.IRepresentation;

public interface ProblemSpaceGraphUpdater
	extends BiFunction<
		DirectedAcyclicGraph<IRepresentation, AProblemStateTransition>,
		Set<IRepresentation>,
		DirectedAcyclicGraph<IRepresentation, AProblemStateTransition>> {
}