package com.tregouet.occam.data.representations.transitions.impl;

import java.util.Objects;

import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.ITransition;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.representations.transitions.Salience;
import com.tregouet.occam.data.representations.transitions.TransitionType;

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
	public String getName() {
		return name;
	}

	@Override
	public IConceptTransitionOIC getOutputInternConfiguration() {
		return outputInternConfig;
	}

	@Override
	public Salience getSalience() {
		return salience;
	}

	@Override
	public int hashCode() {
		return Objects.hash(inputConfig, outputInternConfig);
	}

	@Override
	public void setSalience(Salience salience) {
		this.salience = salience;
	}

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		sB.append("(").append(inputConfig.toString()).append(", ").append(inputConfig.getStackSymbol().toString())
				.append(" -> ").append(outputInternConfig.getPushedStackSymbols().toString()).append(")");
		return sB.toString();
	}

	@Override
	abstract public TransitionType type();

}
