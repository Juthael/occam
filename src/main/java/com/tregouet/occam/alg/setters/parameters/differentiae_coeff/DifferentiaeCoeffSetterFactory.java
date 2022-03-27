package com.tregouet.occam.alg.setters.parameters.differentiae_coeff;

import com.tregouet.occam.alg.setters.parameters.differentiae_coeff.impl.NoCoeff;

public class DifferentiaeCoeffSetterFactory {
	
	public static final DifferentiaeCoeffSetterFactory INSTANCE = new DifferentiaeCoeffSetterFactory(); 
	
	private DifferentiaeCoeffSetterFactory() {
	}
	
	public DifferentiaeCoeffSetter apply(DifferentiaeCoeffSetterStrategy strategy) {
		switch (strategy) {
			case NO_COEFF : 
				return NoCoeff.INSTANCE;
			default : 
				return null;
		}
	}

}
