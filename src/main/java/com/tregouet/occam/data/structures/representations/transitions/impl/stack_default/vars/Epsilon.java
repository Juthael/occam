package com.tregouet.occam.data.structures.representations.transitions.impl.stack_default.vars;

import com.tregouet.occam.data.structures.languages.alphabets.impl.Variable;

public class Epsilon extends Variable {

	public static final Epsilon INSTANCE = new Epsilon();

	private Epsilon() {
		super("ε");
	}

}
