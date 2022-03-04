package com.tregouet.occam.data.languages.specific.impl;

import com.tregouet.occam.data.languages.specific.IBasicProduction;

public class Epsilon extends BasicProduction implements IBasicProduction {

	public static final Epsilon INSTANCE = new Epsilon();
	
	private Epsilon() {
		super(null, null);
	}
	
	@Override
	public boolean isEpsilon() {
		return true;
	}

}
