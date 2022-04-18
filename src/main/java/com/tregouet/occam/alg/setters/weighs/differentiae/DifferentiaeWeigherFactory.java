package com.tregouet.occam.alg.setters.weighs.differentiae;

import com.tregouet.occam.alg.setters.weighs.differentiae.impl.SumOfPropertyWeights;

public class DifferentiaeWeigherFactory {

	public static final DifferentiaeWeigherFactory INSTANCE = new DifferentiaeWeigherFactory();

	private DifferentiaeWeigherFactory() {
	}

	public DifferentiaeWeigher apply(DifferentiaeWeigherStrategy strategy) {
		switch (strategy) {
			case SUM_OF_PROPERTY_WEIGHTS :
				return SumOfPropertyWeights.INSTANCE;
			default :
				return null;
		}
	}

}
