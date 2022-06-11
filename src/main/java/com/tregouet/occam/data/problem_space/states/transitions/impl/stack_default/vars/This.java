package com.tregouet.occam.data.problem_space.states.transitions.impl.stack_default.vars;

import com.tregouet.occam.data.logical_structures.languages.alphabets.impl.Variable;

public class This extends Variable {

	public static final This INSTANCE = new This();
	
	private This() {
		super("this");
	}

}
