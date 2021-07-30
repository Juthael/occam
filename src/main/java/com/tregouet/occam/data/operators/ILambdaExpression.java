package com.tregouet.occam.data.operators;

import com.tregouet.occam.data.constructs.AVariable;

public interface ILambdaExpression {
	
	boolean bindsVar(AVariable boundVar);
	
	void setArgument(ILambdaExpression argument);
	
	String toString();

}
