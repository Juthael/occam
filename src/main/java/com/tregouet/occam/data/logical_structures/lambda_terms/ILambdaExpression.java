package com.tregouet.occam.data.logical_structures.lambda_terms;

import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.IComputation;

public interface ILambdaExpression {

	boolean abstractAndApply(IComputation computation);

	@Override
	boolean equals(Object o);

	@Override
	int hashCode();

	boolean isAnApplication();

	String toShorterString();

	@Override
	String toString();

}
