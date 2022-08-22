package com.tregouet.occam.data.representations.transitions.impl.stack_default.vars;

import com.tregouet.occam.data.structures.languages.alphabets.impl.Variable;

public class Nothing extends Variable {

	public static final Nothing INSTANCE = new Nothing();

	private Nothing() {
		super("ø");
	}

}
