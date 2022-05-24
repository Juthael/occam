package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae;

import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.properties.PropertyBuilder;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;

@FunctionalInterface
public interface DifferentiaeBuilder extends Function<IRepresentationTransitionFunction, Set<ADifferentiae>> {

	public static PropertyBuilder propertyBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getPropertyBuilder();
	}

}
