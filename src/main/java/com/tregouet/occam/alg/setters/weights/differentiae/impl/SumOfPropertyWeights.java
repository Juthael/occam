package com.tregouet.occam.alg.setters.weights.differentiae.impl;

import com.tregouet.occam.alg.setters.weights.differentiae.DifferentiaeWeigher;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;

public class SumOfPropertyWeights implements DifferentiaeWeigher {

	public static final SumOfPropertyWeights INSTANCE = new SumOfPropertyWeights();

	private SumOfPropertyWeights() {
	}

	@Override
	public void accept(ADifferentiae differentiae) {
		double weight = 0.0;
		for (IProperty property : differentiae.getProperties())
			weight += property.weight();
		differentiae.setCoeffFreeWeight(weight);
	}

}
