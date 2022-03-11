package com.tregouet.occam.data.concepts.transitions.dimensions;

import com.tregouet.occam.data.languages.generic.impl.Variable;

public class Something extends Variable {
	
	public static final Something INSTANCE = new Something();
	
	private Something() {
		super("sth");
	}

}
