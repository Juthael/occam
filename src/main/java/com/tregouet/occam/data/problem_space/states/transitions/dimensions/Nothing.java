package com.tregouet.occam.data.problem_space.states.transitions.dimensions;

import com.tregouet.occam.data.logical_structures.languages.alphabets.impl.Variable;

public class Nothing extends Variable {

	public static final Nothing INSTANCE = new Nothing();

	private Nothing() {
		super("ø");
	}

}
