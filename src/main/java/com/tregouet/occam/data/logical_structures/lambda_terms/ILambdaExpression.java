package com.tregouet.occam.data.logical_structures.lambda_terms;

import com.tregouet.occam.data.representations.transitions.productions.IProduction;

public interface ILambdaExpression {
	
	@Override
	boolean equals(Object o);
	
	@Override
	int hashCode();
	
	boolean abstractAndApplyAccordingTo(IProduction production);
	
	@Override
	String toString();
	
	String toShorterString();
	
	boolean isAnApplication();

}
