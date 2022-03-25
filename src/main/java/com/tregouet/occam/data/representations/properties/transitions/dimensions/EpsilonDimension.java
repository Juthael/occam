package com.tregouet.occam.data.representations.properties.transitions.dimensions;

import com.tregouet.occam.data.languages.alphabets.generic.impl.Variable;

public class EpsilonDimension extends Variable {
	
	public static final EpsilonDimension INSTANCE = new EpsilonDimension();
	
	private EpsilonDimension() {
		super("ignore");
	}

}
