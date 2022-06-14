package com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.impl;

import java.util.Arrays;

import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.problem_space.states.productions.IProduction;
import com.tregouet.occam.data.problem_space.states.productions.impl.EpsilonProd;

public class EpsilonComputation extends Computation implements IComputation {

	public static final EpsilonComputation INSTANCE = new EpsilonComputation();

	private EpsilonComputation() {
		super(null, null, Arrays.asList(new IProduction[] {EpsilonProd.INSTANCE}), null);
	}

	@Override
	public boolean isEpsilon() {
		return true;
	}

}
