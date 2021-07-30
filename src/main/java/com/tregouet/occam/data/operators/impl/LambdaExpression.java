package com.tregouet.occam.data.operators.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.constructs.AVariable;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.operators.ILambdaExpression;

public class LambdaExpression implements ILambdaExpression {

	private final IConstruct construct;
	private final List<AVariable> boundVars;
	private List<ILambdaExpression> arguments = new ArrayList<>();
	
	public LambdaExpression(IConstruct construct) {
		this.construct = construct;
		boundVars = construct.getVariables();
		for (AVariable variable : boundVars)
			arguments.add(new ArgumentPlaceholder(variable));
	}
	
	protected LambdaExpression() {
		construct = null;
		boundVars = null;
	}

	@Override
	public boolean bindsVar(AVariable boundVar) {
		return boundVars.contains(boundVar);
	}

	@Override
	public boolean setArgument(AVariable boundVar, ILambdaExpression argument) {
		int varIdx = boundVars.indexOf(boundVar);
		if (varIdx != -1) {
			arguments.remove(varIdx);
			arguments.add(varIdx, argument);
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		sB.append("(λ");
		for (AVariable variable : boundVars) {
			sB.append(variable.toString());
		}
		sB.append(".");
		sB.append(construct.toString());
		sB.append(")");
		for (ILambdaExpression arg : arguments) {
			sB.append(arg.toString());
		}
		return sB.toString();
	}

}
