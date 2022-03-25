package com.tregouet.occam.data.languages.alphabets.domain_specific.impl;

import com.tregouet.occam.data.languages.alphabets.domain_specific.IProduction;

public class EpsilonProd extends Production implements IProduction {

	public static final EpsilonProd INSTANCE = new EpsilonProd();
	
	private EpsilonProd() {
		super(null, null);
	}
	
	@Override
	public boolean isEpsilon() {
		return true;
	}

}
