package com.tregouet.occam.alg.setters.weighs.differentiae.impl;

import com.tregouet.occam.alg.setters.weighs.differentiae.DifferentiaeWeigher;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.IProperty;

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
