package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.impl;

import java.util.Set;

import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.DifferentiaeBuilder;
import com.tregouet.occam.alg.setters.weights.properties.PropertyWeigher;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.IDifferentiationSet;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;

public class WithoutDifferentiation extends ADifferentiaeBuilder implements DifferentiaeBuilder {

	public static final WithoutDifferentiation INSTANCE = new WithoutDifferentiation();

	private WithoutDifferentiation() {
	}

	@Override
	protected IDifferentiationSet buildDifferentiationSet(Set<IProperty> properties, PropertyWeigher propWeigher) {
		return null;
	}

}
