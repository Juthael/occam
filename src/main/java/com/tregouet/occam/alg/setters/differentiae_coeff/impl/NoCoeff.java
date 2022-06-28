package com.tregouet.occam.alg.setters.differentiae_coeff.impl;

import com.tregouet.occam.alg.setters.differentiae_coeff.DifferentiaeCoeffSetter;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
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
