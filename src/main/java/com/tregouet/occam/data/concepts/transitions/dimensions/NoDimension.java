package com.tregouet.occam.data.concepts.transitions.dimensions;

import com.tregouet.occam.data.languages.generic.impl.Variable;

public class NoDimension extends Variable {
	
	public static final NoDimension INSTANCE = new NoDimension();
	
	private NoDimension() {
		super("Ø");
	}

}
