package com.tregouet.occam.data.structures.lambda_terms.impl;

import java.util.List;
import java.util.Objects;

import com.tregouet.occam.data.structures.lambda_terms.IBindings;
import com.tregouet.occam.data.structures.languages.alphabets.AVariable;

public class Bindings implements IBindings {

	private final List<AVariable> boundVariables;

	public Bindings(List<AVariable> boundVariables){
		this.boundVariables = boundVariables;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Bindings other = (Bindings) obj;
		return Objects.equals(boundVariables, other.boundVariables);
	}

	@Override
	public List<AVariable> getBoundVariables() {
		return boundVariables;
	}

	@Override
	public int hashCode() {
		return Objects.hash(boundVariables);
	}

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		for (AVariable boundVariable : boundVariables)
			sB.append(boundVariable);
		return sB.toString();
	}

}
