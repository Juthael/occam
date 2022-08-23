package com.tregouet.occam.data.structures.representations.productions.impl;

import com.tregouet.occam.data.structures.representations.productions.IBasicProduction;

public class EpsilonProd extends BasicProduction implements IBasicProduction {

	public static final EpsilonProd INSTANCE = new EpsilonProd();

	private EpsilonProd() {
		super(null, null);
	}

	@Override
	public boolean isEpsilon() {
		return true;
	}

	@Override
	public String toString() {
		return "ε";
	}

}
