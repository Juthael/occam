package com.tregouet.occam.data.logical_structures.lambda_terms;

import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.applications.IAbstractionApplication;

public interface ILambdaExpression {

	boolean abstractAndApply(IAbstractionApplication abstrApp);

	@Override
	boolean equals(Object o);

	@Override
	int hashCode();

	boolean isAnApplication();

	String toShorterString();

	@Override
	String toString();

}
