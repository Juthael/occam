package com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.applications;

public class EpsilonOperator extends AbstractionApplication implements IAbstractionApplication {

	public static final EpsilonOperator INSTANCE = new EpsilonOperator();

	private EpsilonOperator() {
		super(OperatorType.EPSILON);
	}

	@Override
	public boolean isEpsilonOperator() {
		return true;
	}

	@Override
	public boolean isIdentityOperator() {
		return false;
	}

}
