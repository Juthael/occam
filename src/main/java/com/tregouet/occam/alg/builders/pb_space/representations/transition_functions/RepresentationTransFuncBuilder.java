package com.tregouet.occam.alg.builders.pb_space.representations.transition_functions;

import java.util.function.BiFunction;

import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.descriptions.IDescription;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;

@FunctionalInterface
public interface RepresentationTransFuncBuilder extends
		BiFunction<IClassification, IDescription, IRepresentationTransitionFunction> {

}
