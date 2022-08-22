package com.tregouet.occam.data.representations.transitions.impl;

import java.util.Objects;

import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.representations.transitions.TransitionType;

public abstract class ConceptTransition implements IConceptTransition {

	private final IConceptTransitionIC inputConfig;
	private final IConceptTransitionOIC outputInternConfig;

	public ConceptTransition(IConceptTransitionIC inputConfig, IConceptTransitionOIC outputInternConfig) {
		this.inputConfig = inputConfig;
		this.outputInternConfig = outputInternConfig;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		ConceptTransition other = (ConceptTransition) obj;
		return Objects.equals(inputConfig, other.inputConfig)
				&& Objects.equals(outputInternConfig, other.outputInternConfig);
	}

	@Override
	public IConceptTransitionIC getInputConfiguration() {
		return inputConfig;
	}

	@Override
	public IConceptTransitionOIC getOutputInternConfiguration() {
		return outputInternConfig;
	}

	@Override
	public int hashCode() {
		return Objects.hash(inputConfig, outputInternConfig);
	}

	@Override
	abstract public TransitionType type();

}
