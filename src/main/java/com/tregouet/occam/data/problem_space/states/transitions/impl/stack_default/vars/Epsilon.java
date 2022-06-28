package com.tregouet.occam.data.problem_space.states.transitions.impl.stack_default.vars;

import com.tregouet.occam.data.logical_structures.languages.alphabets.impl.Variable;

public class Epsilon extends Variable {

	public static final Epsilon INSTANCE = new Epsilon();

	private Epsilon() {
		super("ε");
	}

}
