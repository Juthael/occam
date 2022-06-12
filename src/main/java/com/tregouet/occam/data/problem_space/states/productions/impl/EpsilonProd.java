package com.tregouet.occam.data.problem_space.states.productions.impl;

import com.tregouet.occam.data.problem_space.states.productions.IProduction;

public class EpsilonProd extends Production implements IProduction {

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
