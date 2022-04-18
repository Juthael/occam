package com.tregouet.occam.alg.setters.weighs.properties;

import com.tregouet.occam.alg.setters.weighs.properties.impl.BoundVarCounter;

public class PropertyWeigherFactory {

	public static final PropertyWeigherFactory INSTANCE = new PropertyWeigherFactory();

	private PropertyWeigherFactory() {
	}

	public PropertyWeigher apply(PropertyWeigherStrategy strategy) {
		switch (strategy) {
		case NB_OF_BOUND_VAR:
			return BoundVarCounter.INSTANCE;
		default:
			return null;
		}
	}

}
