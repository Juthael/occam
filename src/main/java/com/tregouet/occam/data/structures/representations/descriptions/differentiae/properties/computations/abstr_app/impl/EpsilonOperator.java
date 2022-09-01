package com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.abstr_app.impl;

import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.abstr_app.IAbstractionApplication;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.abstr_app.OperatorType;

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
