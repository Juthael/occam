package com.tregouet.occam.alg.setters.weighs.properties.impl;

import com.tregouet.occam.alg.setters.weighs.properties.PropertyWeigher;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.IComputation;

public class NbOfComputations implements PropertyWeigher {

	public static final NbOfComputations INSTANCE = new NbOfComputations();

	private NbOfComputations() {
	}

	@Override
	public void accept(IProperty property) {
		int weight = 0;
		for (IComputation computation : property.getComputations()) {
			if (!computation.returnsInput())
				weight++;
		}
		property.setWeight(weight);
	}

}
