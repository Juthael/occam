package com.tregouet.occam.data.representations.properties.transitions.impl;

import java.util.Objects;

import com.tregouet.occam.data.languages.alphabets.domain_specific.IContextualizedProduction;
import com.tregouet.occam.data.languages.alphabets.generic.AVariable;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransitionIC;

public class ConceptTransitionIC implements IConceptTransitionIC {

	private final int inputStateID;
	private final IContextualizedProduction inputSymbol;
	private final AVariable stackSymbol;
	
	public ConceptTransitionIC(int inputStateID, IContextualizedProduction inputSymbol, AVariable stackSymbol) {
		this.inputStateID = inputStateID;
		this.inputSymbol = inputSymbol;
		this.stackSymbol = stackSymbol;
	}

	@Override
	public AVariable getRequiredStackSymbol() {
		return stackSymbol;
	}

	@Override
	public IContextualizedProduction getInputSymbol() {
		return inputSymbol;
	}

	@Override
	public int getRequiredInputStateID() {
		return inputStateID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(inputStateID, inputSymbol, stackSymbol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConceptTransitionIC other = (ConceptTransitionIC) obj;
		return inputStateID == other.inputStateID && Objects.equals(inputSymbol, other.inputSymbol)
				&& Objects.equals(stackSymbol, other.stackSymbol);
	}

}
