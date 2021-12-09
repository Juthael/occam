package com.tregouet.occam.data.languages.lambda;

import com.tregouet.occam.data.languages.generic.AVariable;

public interface ILambdaExpression {
	
	boolean appliesAFunction();
	
	boolean binds(AVariable boundVar);
	
	@Override
	boolean equals(Object o);
	
	@Override
	int hashCode();
	
	boolean setArgument(AVariable boundVar, ILambdaExpression argument);
	
	@Override
	String toString();

}
