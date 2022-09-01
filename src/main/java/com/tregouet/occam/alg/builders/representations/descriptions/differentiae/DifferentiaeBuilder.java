package com.tregouet.occam.alg.builders.representations.descriptions.differentiae;

import java.util.Set;
import java.util.function.BiFunction;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.DifferentiationSetBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.differentiation.DifferentiationBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties.PropertyBuilder;
import com.tregouet.occam.alg.setters.SettersAbstractFactory;
import com.tregouet.occam.alg.setters.weights.properties.PropertyWeigher;
import com.tregouet.occam.data.structures.representations.classifications.IClassification;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.structures.representations.productions.IContextualizedProduction;

@FunctionalInterface
public interface DifferentiaeBuilder extends BiFunction<IClassification, Set<IContextualizedProduction>, Set<ADifferentiae>> {

	public static DifferentiationBuilder differentiationBuilder() {
		return BuildersAbstractFactory.INSTANCE.getDifferentiationBuilder();
	}

	public static DifferentiationSetBuilder differentiationSetBuilder() {
		return BuildersAbstractFactory.INSTANCE.getDifferentiationSetBuilder();
	}

	public static PropertyBuilder propertyBuilder() {
		return BuildersAbstractFactory.INSTANCE.getPropertyBuilder();
	}

	public static PropertyWeigher propertyWeigher() {
		return SettersAbstractFactory.INSTANCE.getPropertyWeigher();
	}

}
