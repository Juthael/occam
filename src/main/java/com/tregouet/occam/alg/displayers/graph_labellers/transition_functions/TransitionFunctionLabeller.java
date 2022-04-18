package com.tregouet.occam.alg.displayers.graph_labellers.transition_functions;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.google.common.base.Function;
import com.tregouet.occam.data.representations.transitions.AConceptTransitionSet;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;

public interface TransitionFunctionLabeller
		extends Function<IRepresentationTransitionFunction, DirectedAcyclicGraph<Integer, AConceptTransitionSet>> {

}
