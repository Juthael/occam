package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties;

import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.alg.weighers.WeighersAbstractFactory;
import com.tregouet.occam.alg.weighers.properties.PropertyWeigher;
import com.tregouet.occam.data.representations.properties.IProperty;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;

@FunctionalInterface
public interface PropertyBuilder extends Function<IRepresentationTransitionFunction, Set<IProperty>> {
	
	public static PropertyWeigher weigher() {
		return WeighersAbstractFactory.INSTANCE.getPropertyWheigher();
	}

}
