package com.tregouet.occam.alg.setters.weights.differentiae.coeff.impl;

import com.tregouet.occam.alg.setters.weights.differentiae.coeff.DifferentiaeCoeffSetter;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IIsA;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.InvertedTree;

public class NoCoeff implements DifferentiaeCoeffSetter {

	public static final NoCoeff INSTANCE = new NoCoeff();

	private NoCoeff() {
	}

	@Override
	public void accept(ADifferentiae aDifferentiae) {
		aDifferentiae.setWeightCoeff(1);
	}

	@Override
	public NoCoeff setContext(InvertedTree<IConcept, IIsA> conceptTree) {
		return this;
	}

}
