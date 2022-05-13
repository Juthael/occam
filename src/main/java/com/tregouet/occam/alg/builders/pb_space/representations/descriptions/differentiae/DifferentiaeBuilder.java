package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae;

import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.properties.PropertyBuilder;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;

@FunctionalInterface
public interface DifferentiaeBuilder extends Function<IRepresentationTransitionFunction, Set<AbstractDifferentiae>> {

	public static PropertyBuilder propertyBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getPropertyBuilder();
	}

}
