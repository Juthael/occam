package com.tregouet.occam.alg.setters.weighs.properties.impl;

import com.tregouet.occam.alg.setters.weighs.properties.PropertyWeigher;
import com.tregouet.occam.data.structures.representations.classifications.IClassification;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;

public class Weightless implements PropertyWeigher {

	public static final Weightless INSTANCE = new Weightless();

	private Weightless() {
	}

	@Override
	public void accept(IProperty property) {
		property.setWeight(0.0);
	}

	@Override
	public PropertyWeigher setUp(IClassification classification) {
		return this;
	}

}
