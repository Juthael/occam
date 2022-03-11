package com.tregouet.occam.data.concepts.transitions.dimensions;

import com.tregouet.occam.data.languages.generic.impl.Variable;

public class Nothing extends Variable {
	
	public static final Nothing INSTANCE = new Nothing();
	
	private Nothing() {
		super("Ø");
	}

}
