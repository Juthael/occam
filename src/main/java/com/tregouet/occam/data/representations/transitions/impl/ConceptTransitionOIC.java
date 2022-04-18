package com.tregouet.occam.data.representations.transitions.impl;

import java.util.List;
import java.util.Objects;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionOIC;

public class ConceptTransitionOIC implements IConceptTransitionOIC {

	private final int outputStateID;
	private final List<AVariable> pushedStackSymbols;

	public ConceptTransitionOIC(int outputStateID, List<AVariable> nextStackSymbols) {
		this.outputStateID = outputStateID;
		this.pushedStackSymbols = nextStackSymbols;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		ConceptTransitionOIC other = (ConceptTransitionOIC) obj;
		return Objects.equals(pushedStackSymbols, other.pushedStackSymbols) && outputStateID == other.outputStateID;
	}

	@Override
	public int getOutputStateID() {
		return outputStateID;
	}

	@Override
	public List<AVariable> getPushedStackSymbols() {
		return pushedStackSymbols;
	}

	@Override
	public int hashCode() {
		return Objects.hash(pushedStackSymbols, outputStateID);
	}

}
