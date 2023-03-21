package com.tregouet.occam.alg.setters.weights.properties;

import com.tregouet.occam.alg.setters.weights.properties.impl.NbOfComputations;
import com.tregouet.occam.alg.setters.weights.properties.impl.Ruleness;
import com.tregouet.occam.alg.setters.weights.properties.impl.RulenessAndEconomy;
import com.tregouet.occam.alg.setters.weights.properties.impl.RulenessDimensionality;
import com.tregouet.occam.alg.setters.weights.properties.impl.Weightless;

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
		case RULENESS_AND_ECONOMY :
			return new RulenessAndEconomy();
		case RULENESS_DIMENSIONALITY : 
			return new RulenessDimensionality();
		default:
			return null;
		}
	}

}
