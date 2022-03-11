package com.tregouet.occam.data.concepts.transitions.dimensions;

import com.tregouet.occam.data.languages.generic.impl.Variable;

public class This extends Variable {
	
	public static final This INSTANCE = new This();
	
	private This() {
		super("this");
	}

}
