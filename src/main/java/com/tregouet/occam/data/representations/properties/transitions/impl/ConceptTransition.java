package com.tregouet.occam.data.representations.properties.transitions.impl;

import java.util.Objects;

import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.ITransition;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.representations.properties.transitions.Salience;
import com.tregouet.occam.data.representations.properties.transitions.TransitionType;

public abstract class ConceptTransition implements IConceptTransition {

	private final String name;
	private final IConceptTransitionIC inputConfig;
	private final IConceptTransitionOIC outputInternConfig;
	private Salience salience;
	
	public ConceptTransition(IConceptTransitionIC inputConfig, IConceptTransitionOIC outputInternConfig) {
		name = ITransition.provideName();
		this.inputConfig = inputConfig;
		this.outputInternConfig = outputInternConfig;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IConceptTransitionIC getInputConfiguration() {
		return inputConfig;
	}

	@Override
	public int hashCode() {
		return Objects.hash(inputConfig, outputInternConfig);
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
		return Objects.equals(inputConfig, other.inputConfig)
				&& Objects.equals(outputInternConfig, other.outputInternConfig);
	}

	@Override
	public IConceptTransitionOIC getOutputInternConfiguration() {
		return outputInternConfig;
	}

	@Override
	public void setSalience(Salience salience) {
		this.salience = salience;
	}

	@Override
	public Salience getSalience() {
		return salience;
	}

	@Override
	abstract public TransitionType type();

}
