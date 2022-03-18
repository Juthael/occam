package com.tregouet.occam.data.representations.transitions.impl;

import java.util.List;
import java.util.Objects;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionOIC;

public class ConceptTransitionOIC implements IConceptTransitionOIC {

	private final int outputStateID;
	private final List<AVariable> nextStackSymbols;
	
	public ConceptTransitionOIC(int outputStateID, List<AVariable> nextStackSymbols) {
		this.outputStateID = outputStateID;
		this.nextStackSymbols = nextStackSymbols;
	}

	@Override
	public List<AVariable> getOutputStackSymbols() {
		return nextStackSymbols;
	}

	@Override
	public int getOutputStateID() {
		return outputStateID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nextStackSymbols, outputStateID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConceptTransitionOIC other = (ConceptTransitionOIC) obj;
		return Objects.equals(nextStackSymbols, other.nextStackSymbols) && outputStateID == other.outputStateID;
	}

}
