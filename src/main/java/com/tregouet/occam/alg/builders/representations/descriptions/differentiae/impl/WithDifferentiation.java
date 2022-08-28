package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.impl;

import java.util.Set;

import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.DifferentiaeBuilder;
import com.tregouet.occam.alg.setters.weights.properties.PropertyWeigher;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.IDifferentiationSet;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;

public class WithDifferentiation extends ADifferentiaeBuilder implements DifferentiaeBuilder {

	public static final WithDifferentiation INSTANCE = new WithDifferentiation();

	private WithDifferentiation() {
	}

	@Override
	protected IDifferentiationSet buildDifferentiationSet(Set<IProperty> properties, PropertyWeigher propWeigher) {
		return DifferentiaeBuilder
				.differentiationSetBuilder().setUp(
						DifferentiaeBuilder.differentiationBuilder().setUp(propWeigher))
				.apply(properties);
	}

}
