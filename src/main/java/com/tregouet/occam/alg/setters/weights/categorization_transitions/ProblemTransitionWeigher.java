package com.tregouet.occam.alg.setters.weights.categorization_transitions;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.setters.weights.Weigher;
import com.tregouet.occam.data.modules.sorting.transitions.AProblemStateTransition;
import com.tregouet.occam.data.structures.representations.IRepresentation;

public interface ProblemTransitionWeigher extends Weigher<AProblemStateTransition> {

	ProblemTransitionWeigher setContext(
			DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph);

}
