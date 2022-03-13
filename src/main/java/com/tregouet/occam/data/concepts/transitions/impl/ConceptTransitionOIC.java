package com.tregouet.occam.data.concepts.transitions.impl;

import java.util.List;
import java.util.Objects;

import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.transitions.output_config.impl.OutputInternConfiguration;
import com.tregouet.occam.data.concepts.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.languages.generic.AVariable;

public class ConceptTransitionOIC extends OutputInternConfiguration implements IConceptTransitionOIC {

	private final List<AVariable> nextStackSymbols;
	
	public ConceptTransitionOIC(IState outputState, List<AVariable> nextStackSymbols) {
		super(outputState);
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

}