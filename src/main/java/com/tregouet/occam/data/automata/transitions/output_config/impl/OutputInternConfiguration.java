package com.tregouet.occam.data.automata.transitions.output_config.impl;

import java.util.Objects;

import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.transitions.output_config.IOutputInternConfiguration;

public class OutputInternConfiguration implements IOutputInternConfiguration {

	private final IState outputState;
	
	public OutputInternConfiguration(
			IState outputState) {
		this.outputState = outputState;
	}
	
	@Override
	public IState getOutputState() {
		return outputState;
	}

	@Override
	public int hashCode() {
		return Objects.hash(outputState);
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
		return Objects.equals(outputState, other.outputState);
	}

}
