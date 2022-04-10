package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties;

import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.alg.setters.SettersAbstractFactory;
import com.tregouet.occam.alg.setters.weighs.properties.PropertyWeigher;
import com.tregouet.occam.data.representations.descriptions.properties.IProperty;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;

@FunctionalInterface
public interface PropertyBuilder extends Function<IRepresentationTransitionFunction, Set<IProperty>> {
	
	public static PropertyWeigher propertyWeigher() {
		return SettersAbstractFactory.INSTANCE.getPropertyWheigher();
	}

}
