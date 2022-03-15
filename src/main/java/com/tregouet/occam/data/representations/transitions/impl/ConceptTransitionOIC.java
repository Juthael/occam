package com.tregouet.occam.data.representations.transitions.impl;

import java.util.List;
import java.util.Objects;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.representations.IConcept;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionOIC;

public class ConceptTransitionOIC implements IConceptTransitionOIC {

	private final IConcept outputState;
	private final List<AVariable> nextStackSymbols;
	
	public ConceptTransitionOIC(IConcept outputState, List<AVariable> nextStackSymbols) {
		this.outputState = outputState;
		this.nextStackSymbols = nextStackSymbols;
	}

	@Override
	public List<AVariable> getOutputStackSymbols() {
		return nextStackSymbols;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(nextStackSymbols);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConceptTransitionOIC other = (ConceptTransitionOIC) obj;
		return Objects.equals(nextStackSymbols, other.nextStackSymbols);
	}

	@Override
	public IState getOutputState() {
		return outputState;
	}

}
