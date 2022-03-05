package com.tregouet.occam.data.languages.specific.impl;

import com.tregouet.occam.data.languages.specific.IProduction;

public class Epsilon extends Production implements IProduction {

	public static final Epsilon INSTANCE = new Epsilon();
	
	private Epsilon() {
		super(null, null);
	}
	
	@Override
	public boolean isEpsilon() {
		return true;
	}

}
