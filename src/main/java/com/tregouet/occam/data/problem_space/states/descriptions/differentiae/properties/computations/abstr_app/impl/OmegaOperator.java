package com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.abstr_app.impl;

import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.abstr_app.IAbstractionApplication;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.abstr_app.OperatorType;

public class OmegaOperator extends AbstractionApplication implements IAbstractionApplication {

	public static final OmegaOperator INSTANCE = new OmegaOperator();

	private OmegaOperator() {
		super(OperatorType.OMEGA);
	}

}
