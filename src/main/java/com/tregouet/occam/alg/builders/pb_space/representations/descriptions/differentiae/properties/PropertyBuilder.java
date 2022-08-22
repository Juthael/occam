package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.properties;

import java.util.Set;
import java.util.function.BiFunction;

import com.tregouet.occam.alg.setters.SettersAbstractFactory;
import com.tregouet.occam.alg.setters.weighs.properties.PropertyWeigher;
import com.tregouet.occam.data.representations.classifications.IClassification;
import com.tregouet.occam.data.representations.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.representations.productions.IContextualizedProduction;

@FunctionalInterface
public interface PropertyBuilder extends BiFunction<IClassification, Set<IContextualizedProduction>, Set<IProperty>> {

	public static PropertyWeigher propertyWeigher() {
		return SettersAbstractFactory.INSTANCE.getPropertyWheigher();
	}

}
