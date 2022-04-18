package com.tregouet.occam.data.logical_structures.lambda_terms;

import com.tregouet.occam.data.representations.transitions.productions.IProduction;

public interface ILambdaExpression {

	boolean abstractAndApplyAccordingTo(IProduction production);

	@Override
	boolean equals(Object o);

	@Override
	int hashCode();

	boolean isAnApplication();

	String toShorterString();

	@Override
	String toString();

}
