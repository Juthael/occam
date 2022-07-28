package com.tregouet.occam.alg.setters.weighs.properties;

import com.tregouet.occam.alg.setters.weighs.properties.impl.NbOfComputations;
import com.tregouet.occam.alg.setters.weighs.properties.impl.Ruleness;
import com.tregouet.occam.alg.setters.weighs.properties.impl.Weightless;

public class PropertyWeigherFactory {

	public static final PropertyWeigherFactory INSTANCE = new PropertyWeigherFactory();

	private PropertyWeigherFactory() {
	}

	public PropertyWeigher apply(PropertyWeigherStrategy strategy) {
		switch (strategy) {
		case WEIGHTLESS :
			return Weightless.INSTANCE;
		case NB_OF_COMPUTATIONS :
			return NbOfComputations.INSTANCE;
		case RULENESS : 
			return new Ruleness();
		default:
			return null;
		}
	}

}
