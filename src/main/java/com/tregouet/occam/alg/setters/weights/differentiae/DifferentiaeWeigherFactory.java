package com.tregouet.occam.alg.setters.weights.differentiae;

import com.tregouet.occam.alg.setters.weights.differentiae.impl.MaxWeightForNonRedundantSubsetOfProp;

public class DifferentiaeWeigherFactory {

	public static final DifferentiaeWeigherFactory INSTANCE = new DifferentiaeWeigherFactory();

	private DifferentiaeWeigherFactory() {
	}

	public DifferentiaeWeigher apply(DifferentiaeWeigherStrategy strategy) {
		switch (strategy) {
		case MAX_WEIGHT_FOR_NON_REDUNDANT :
			return MaxWeightForNonRedundantSubsetOfProp.INSTANCE;
		default:
			return null;
		}
	}

}