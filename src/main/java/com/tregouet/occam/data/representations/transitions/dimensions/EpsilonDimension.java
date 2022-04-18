package com.tregouet.occam.data.representations.transitions.dimensions;

import com.tregouet.occam.data.logical_structures.languages.alphabets.impl.Variable;

public class EpsilonDimension extends Variable {

	public static final EpsilonDimension INSTANCE = new EpsilonDimension();

	private EpsilonDimension() {
		super("ignore");
	}

}
