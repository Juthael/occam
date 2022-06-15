package com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.applications;

public class OmegaOperator extends AbstractionApplication implements IAbstractionApplication {

	public static final OmegaOperator INSTANCE = new OmegaOperator();

	private OmegaOperator() {
		super(OperatorType.OMEGA);
	}

}
