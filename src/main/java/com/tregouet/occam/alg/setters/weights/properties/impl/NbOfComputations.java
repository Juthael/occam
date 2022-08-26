package com.tregouet.occam.alg.setters.weights.properties.impl;

import com.tregouet.occam.alg.setters.weights.properties.PropertyWeigher;
import com.tregouet.occam.data.structures.representations.classifications.IClassification;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IWeighedProperty;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.IComputation;

public class NbOfComputations implements PropertyWeigher {

	public static final NbOfComputations INSTANCE = new NbOfComputations();

	private NbOfComputations() {
	}

	@Override
	public void accept(IWeighedProperty property) {
		int weight = 0;
		for (IComputation computation : property.getComputations()) {
			if (!computation.returnsInput())
				weight++;
		}
		property.setWeight(weight);
	}

	@Override
	public PropertyWeigher setUp(IClassification classification) {
		return this;
	}

}
