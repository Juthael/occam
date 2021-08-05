package com.tregouet.occam.data.operators;

public interface IArgumentPlaceholder extends ILambdaExpression {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);	

}
