package com.tregouet.occam.data.representations.properties.transitions.dimensions;

import com.tregouet.occam.data.alphabets.generic.impl.Variable;

public class Something extends Variable {
	
	public static final Something INSTANCE = new Something();
	
	private Something() {
		super("sth");
	}

}
