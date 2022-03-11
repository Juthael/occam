package com.tregouet.occam.data.concepts.transitions.impl;

import java.util.Objects;

import com.tregouet.occam.data.automata.transitions.ITransition;
import com.tregouet.occam.data.concepts.transitions.IConceptTransition;

public class ConceptTransition implements IConceptTransition {

	private final String name;
	private final ConceptTransitionIC inputConfig;
	private final ConceptTransitionOIC outputInternConfig;
	
	public ConceptTransition(ConceptTransitionIC inputConfig, ConceptTransitionOIC outputInternConfig) {
		name = ITransition.provideName();
		this.inputConfig = inputConfig;
		this.outputInternConfig = outputInternConfig;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ConceptTransitionIC getInputConfiguration() {
		return inputConfig;
	}

	@Override
	public int hashCode() {
		return Objects.hash(inputConfig, name, outputInternConfig);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConceptTransition other = (ConceptTransition) obj;
		return Objects.equals(inputConfig, other.inputConfig) && Objects.equals(name, other.name)
				&& Objects.equals(outputInternConfig, other.outputInternConfig);
	}

	@Override
	public ConceptTransitionOIC getOutputInternConfiguration() {
		return outputInternConfig;
	}

}
