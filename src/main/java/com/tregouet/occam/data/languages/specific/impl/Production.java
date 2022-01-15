package com.tregouet.occam.data.languages.specific.impl;

import java.util.Objects;

import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.specific.IProduction;

public abstract class Production implements IProduction {
	
	protected AVariable variable;
	
	public Production(AVariable variable) {
		this.variable = variable;
	}
	
	@Override
	public boolean derives(AVariable var) {
		return var.equals(variable);
	}

	@Override
	public AVariable getVariable() {
		return variable;
	}

	@Override
	public int hashCode() {
		return Objects.hash(variable);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Production other = (Production) obj;
		return Objects.equals(variable, other.variable);
	}

}
