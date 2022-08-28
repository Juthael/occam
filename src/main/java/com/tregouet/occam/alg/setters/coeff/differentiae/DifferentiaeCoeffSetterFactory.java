package com.tregouet.occam.alg.setters.coeff.differentiae;

import com.tregouet.occam.alg.setters.coeff.differentiae.impl.NoCoeff;
import com.tregouet.occam.alg.setters.coeff.differentiae.impl.SpeciesCardinality;

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
