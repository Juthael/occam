package com.tregouet.occam.data.representations.transitions.dimensions;

import com.tregouet.occam.data.logical_structures.languages.alphabets.impl.Variable;

public class This extends Variable {

	public static final This INSTANCE = new This();

	private This() {
		super("this");
	}

}
