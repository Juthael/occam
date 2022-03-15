package com.tregouet.occam.data.languages.lambda.impl;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.languages.lambda.IArgumentPlaceholder;
import com.tregouet.occam.data.languages.lambda.ILambdaExpression;

public class ArgumentPlaceholder extends LambdaExpression implements IArgumentPlaceholder {

	private final AVariable toBeDerived;
	
	public ArgumentPlaceholder(AVariable toBeDerived) {
		this.toBeDerived = toBeDerived;
	}
	
	@Override
	public boolean appliesAFunction() {
		return false;
	}
	
	@Override
	public boolean binds(AVariable boundVar) {
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArgumentPlaceholder other = (ArgumentPlaceholder) obj;
		if (toBeDerived == null) {
			if (other.toBeDerived != null)
				return false;
		} else if (!toBeDerived.equals(other.toBeDerived))
			return false;
		return true;
	}
	
	public AVariable getVariableToBeDerived() {
		return toBeDerived;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((toBeDerived == null) ? 0 : toBeDerived.hashCode());
		return result;
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
