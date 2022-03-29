package com.tregouet.occam.alg.builders.representations;

import java.util.function.BiFunction;

import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;

public interface RepresentationBuilder extends BiFunction<IRepresentationTransitionFunction, IDescription, IRepresentation> {

}
