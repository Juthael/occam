package com.tregouet.occam.data.logical_structures.lambda_terms;

import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.IApplication;

public interface ILambdaExpression {

	boolean abstractAndApply(IApplication application);

	@Override
	boolean equals(Object o);

	@Override
	int hashCode();

	boolean isAnApplication();

	String toShorterString();

	@Override
	String toString();

}
