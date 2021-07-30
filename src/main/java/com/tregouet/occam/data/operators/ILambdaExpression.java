package com.tregouet.occam.data.operators;

import com.tregouet.occam.data.constructs.AVariable;

public interface ILambdaExpression {
	
	boolean bindsVar(AVariable boundVar);
	
	boolean setArgument(AVariable boundVar, ILambdaExpression argument);
	
	String toString();

}
