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
		if (construct.isAbstract())
			boundVars = construct.getVariables();
		else boundVars = new ArrayList<>();
		for (AVariable variable : boundVars)
			arguments.add(new ArgumentPlaceholder(variable));
	}
	
	protected LambdaExpression() {
		construct = null;
		boundVars = null;
	}

	@Override
	public boolean appliesAFunction() {
		return (!boundVars.isEmpty());
	}

	@Override
	public boolean binds(AVariable boundVar) {
		return boundVars.contains(boundVar);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LambdaExpression other = (LambdaExpression) obj;
		if (arguments == null) {
			if (other.arguments != null)
				return false;
		} else if (!arguments.equals(other.arguments))
			return false;
		if (boundVars == null) {
			if (other.boundVars != null)
				return false;
		} else if (!boundVars.equals(other.boundVars))
			return false;
		if (construct == null) {
			if (other.construct != null)
				return false;
		} else if (!construct.equals(other.construct))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arguments == null) ? 0 : arguments.hashCode());
		result = prime * result + ((boundVars == null) ? 0 : boundVars.hashCode());
		result = prime * result + ((construct == null) ? 0 : construct.hashCode());
		return result;
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
		if (construct.isAbstract()) {
			StringBuilder sB = new StringBuilder();
			sB.append("(λ");
			for (AVariable variable : boundVars) {
				sB.append(variable.toString());
			}
			sB.append(".");
			sB.append(construct.toString());
			sB.append(")");
			for (ILambdaExpression arg : arguments) {
				sB.append(" ");
				if (arg.appliesAFunction())
					sB.append("(" + arg.toString() + ")");
				else sB.append(arg.toString());
			}
			return sB.toString();	
		}
		else return construct.toString();
	}

}
