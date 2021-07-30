package com.tregouet.occam.data.operators;

import com.tregouet.occam.data.constructs.AVariable;

public interface ILambdaExpression {
	
	public int hashCode();
	
	boolean bindsVar(AVariable boundVar);
	
	boolean equals(Object o);
	
	boolean setArgument(AVariable boundVar, ILambdaExpression argument);
	
	String toString();

}
