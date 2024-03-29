package com.tregouet.occam.alg.setters.weights.differentiae.coeff;

import com.tregouet.occam.alg.setters.weights.differentiae.coeff.impl.NoCoeff;
import com.tregouet.occam.alg.setters.weights.differentiae.coeff.impl.SpeciesCardinality;

public class DifferentiaeCoeffSetterFactory {

	public static final DifferentiaeCoeffSetterFactory INSTANCE = new DifferentiaeCoeffSetterFactory();

	private DifferentiaeCoeffSetterFactory() {
	}

	public DifferentiaeCoeffSetter apply(DifferentiaeCoeffSetterStrategy strategy) {
		switch (strategy) {
		case NO_COEFF:
			return NoCoeff.INSTANCE;
		case SPECIES_CARDINALITY:
			return new SpeciesCardinality();
		default:
			return null;
		}
	}

}
