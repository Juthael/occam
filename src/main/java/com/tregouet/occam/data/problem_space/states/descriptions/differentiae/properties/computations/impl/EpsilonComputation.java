package com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.impl;

import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.applications.EpsilonOperator;

public class EpsilonComputation extends Computation implements IComputation {

	public static final EpsilonComputation INSTANCE = new EpsilonComputation();

	private EpsilonComputation() {
		super(null, EpsilonOperator.INSTANCE, null);
	}

	@Override
	public boolean isEpsilon() {
		return true;
	}

}
