package com.tregouet.occam.data.languages.specific.impl;

import com.tregouet.occam.data.languages.specific.IBasicProduction;

public class Epsilon extends BasicProduction implements IBasicProduction {

	public Epsilon INSTANCE = new Epsilon();
	
	private Epsilon() {
		super(null, null);
	}

}
