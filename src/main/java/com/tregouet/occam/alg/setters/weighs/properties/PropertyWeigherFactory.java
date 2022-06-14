package com.tregouet.occam.alg.setters.weighs.properties;

import com.tregouet.occam.alg.setters.weighs.properties.impl.BoundVarCounter;
import com.tregouet.occam.alg.setters.weighs.properties.impl.NbOfComputations;
import com.tregouet.occam.alg.setters.weighs.properties.impl.Weightless;

public class PropertyWeigherFactory {

	public static final PropertyWeigherFactory INSTANCE = new PropertyWeigherFactory();

	private PropertyWeigherFactory() {
	}

	public PropertyWeigher apply(PropertyWeigherStrategy strategy) {
		switch (strategy) {
		case NB_OF_BOUND_VAR:
			return BoundVarCounter.INSTANCE;
		case WEIGHTLESS :
			return Weightless.INSTANCE;
		case NB_OF_COMPUTATIONS :
			return NbOfComputations.INSTANCE;
		default:
			return null;
		}
	}

}
