package com.tregouet.occam.alg.setters.parameters.differentiae_coeff.impl;

import com.tregouet.occam.alg.setters.parameters.differentiae_coeff.DifferentiaeCoeffSetter;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.properties.AbstractDifferentiae;

public class NoCoeff implements DifferentiaeCoeffSetter {

	public static final NoCoeff INSTANCE = new NoCoeff();
	
	private NoCoeff() {
	}
	
	@Override
	public void accept(AbstractDifferentiae abstractDifferentiae) {
		abstractDifferentiae.setWeightCoeff((double) 1);
	}

	@Override
	public NoCoeff setContext(IDescription description) {
		return this;
	}

}
