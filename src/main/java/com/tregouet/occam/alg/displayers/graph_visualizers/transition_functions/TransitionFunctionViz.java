package com.tregouet.occam.alg.displayers.graph_visualizers.transition_functions;

import java.util.function.BiFunction;

import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;

public interface TransitionFunctionViz extends BiFunction<IRepresentationTransitionFunction, String, String> {

}
