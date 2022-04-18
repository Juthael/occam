package com.tregouet.occam.alg.setters.differentiae_coeff.impl;

import com.tregouet.occam.alg.setters.differentiae_coeff.DifferentiaeCoeffSetter;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

public class NoCoeff implements DifferentiaeCoeffSetter {

	public static final NoCoeff INSTANCE = new NoCoeff();

	private NoCoeff() {
	}

	@Override
	public void accept(AbstractDifferentiae abstractDifferentiae) {
		abstractDifferentiae.setWeightCoeff(1);
	}

	@Override
	public NoCoeff setContext(Tree<Integer, AbstractDifferentiae> classification) {
		return this;
	}

}
