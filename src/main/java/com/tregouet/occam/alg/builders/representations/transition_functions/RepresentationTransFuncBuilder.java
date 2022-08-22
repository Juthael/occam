package com.tregouet.occam.alg.builders.representations.transition_functions;

import java.util.function.BiFunction;

import com.tregouet.occam.data.representations.classifications.IClassification;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;

@FunctionalInterface
public interface RepresentationTransFuncBuilder extends
		BiFunction<IClassification, IDescription, IRepresentationTransitionFunction> {

}
