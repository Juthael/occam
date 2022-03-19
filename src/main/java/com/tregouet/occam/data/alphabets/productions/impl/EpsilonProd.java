package com.tregouet.occam.data.alphabets.productions.impl;

import com.tregouet.occam.data.alphabets.productions.IProduction;

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
