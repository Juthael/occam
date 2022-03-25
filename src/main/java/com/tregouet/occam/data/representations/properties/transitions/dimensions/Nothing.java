package com.tregouet.occam.data.representations.properties.transitions.dimensions;

import com.tregouet.occam.data.languages.alphabets.generic.impl.Variable;

public class Nothing extends Variable {
	
	public static final Nothing INSTANCE = new Nothing();
	
	private Nothing() {
		super("nothing");
	}

}
