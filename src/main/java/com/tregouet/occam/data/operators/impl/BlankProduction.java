package com.tregouet.occam.data.operators.impl;

import java.util.List;

import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.operators.ILambdaExpression;
import com.tregouet.occam.data.operators.IProduction;

public class BlankProduction extends Production implements IProduction {

	private static final long serialVersionUID = -1489727700831533797L;

	public BlankProduction(IIntentAttribute operatorInput, IIntentAttribute operatorOutput) {
		super(operatorInput, operatorOutput);
	}
	
	@Override
	public ILambdaExpression asLambda(List<IProduction> nextProductions) {
		return null;
	}

	@Override
	public ILambdaExpression semanticRule() {
		return null;
	}
	
	@Override
	public String toString() {
		return new String();  
	}

	@Override
	public int hashCode() {
		return System.identityHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return (this == obj);
	}

}