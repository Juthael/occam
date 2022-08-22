package com.tregouet.occam.data.representations.transitions.impl.stack_default.vars;

import com.tregouet.occam.data.structures.languages.alphabets.impl.Variable;

public class This extends Variable {

	public static final This INSTANCE = new This();

	private This() {
		super("this");
	}

}
