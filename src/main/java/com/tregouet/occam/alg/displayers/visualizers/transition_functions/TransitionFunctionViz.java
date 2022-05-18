package com.tregouet.occam.alg.displayers.visualizers.transition_functions;

import java.util.function.BiFunction;

import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;

public interface TransitionFunctionViz extends BiFunction<IRepresentationTransitionFunction, String, String> {

}
