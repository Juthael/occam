package com.tregouet.occam.data.concepts.transitions;

import java.util.Objects;

import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.transitions.output_config.IPushdownAutomatonOIC;
import com.tregouet.occam.data.automata.transitions.output_config.impl.OutputInternConfiguration;
import com.tregouet.occam.data.languages.generic.AVariable;

public class ConceptTransitionOIC extends OutputInternConfiguration implements IPushdownAutomatonOIC<AVariable> {

	private final AVariable nextStackSymbol;
	
	public ConceptTransitionOIC(IState outputState, AVariable nextStackSymbol) {
		super(outputState);
		this.nextStackSymbol = nextStackSymbol;
	}

	@Override
	public AVariable getOutputStackSymbol() {
		return nextStackSymbol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(nextStackSymbol);
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
		return Objects.equals(nextStackSymbol, other.nextStackSymbol);
	}

}
