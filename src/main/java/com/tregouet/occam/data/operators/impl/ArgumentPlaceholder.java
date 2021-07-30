package com.tregouet.occam.data.operators.impl;

import com.tregouet.occam.data.constructs.AVariable;
import com.tregouet.occam.data.operators.IArgumentPlaceholder;
import com.tregouet.occam.data.operators.ILambdaExpression;

public class ArgumentPlaceholder extends LambdaExpression implements IArgumentPlaceholder {

	private final AVariable toBeDerived;
	
	public ArgumentPlaceholder(AVariable toBeDerived) {
		this.toBeDerived = toBeDerived;
	}
	
	public AVariable getVariableToBeDerived() {
		return toBeDerived;
	}
	
	@Override
	public boolean bindsVar(AVariable boundVar) {
		return false;
	}

	@Override
	public boolean setArgument(AVariable boundVar, ILambdaExpression argument) {
		return false;
	}
	
	@Override
	public String toString() {
		return "(" + toBeDerived.toString() + ".deriv)";
	}

}
