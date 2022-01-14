package com.tregouet.occam.data.abstract_machines.transition_rules.impl;

import java.util.Objects;

import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transition_rules.IOutputInternConfiguration;
import com.tregouet.occam.data.languages.generic.AVariable;

public class OutputInternConfiguration implements IOutputInternConfiguration {

	private final IState outputState;
	private final AVariable nextStackSymbol;
	
	public OutputInternConfiguration(IState outputState, AVariable nextStackSymbol) {
		this.outputState = outputState;
		this.nextStackSymbol = nextStackSymbol;
	}
	
	@Override
	public IState getOutputState() {
		return outputState;
	}

	@Override
	public AVariable getOutputStackSymbol() {
		return nextStackSymbol;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nextStackSymbol, outputState);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OutputInternConfiguration other = (OutputInternConfiguration) obj;
		return Objects.equals(nextStackSymbol, other.nextStackSymbol) && Objects.equals(outputState, other.outputState);
	}

}
