package com.tregouet.occam.alg.setters.weighs.differentiae.impl;

import com.tregouet.occam.alg.setters.weighs.differentiae.DifferentiaeWeigher;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.occam.data.representations.descriptions.properties.IProperty;

public class SumOfPropertyWeights implements DifferentiaeWeigher {

	public static final SumOfPropertyWeights INSTANCE = new SumOfPropertyWeights();
	
	private SumOfPropertyWeights() {
	}
	
	@Override
	public void accept(AbstractDifferentiae differentiae) {
		double weight = 0.0;
		for (IProperty property : differentiae.getProperties())
			weight += property.weight();
		differentiae.setCoeffFreeWeight(weight);
	}

}
