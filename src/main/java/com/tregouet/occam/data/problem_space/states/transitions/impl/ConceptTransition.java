package com.tregouet.occam.data.problem_space.states.transitions.impl;

import java.util.Iterator;
import java.util.Objects;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.problem_space.states.transitions.Salience;
import com.tregouet.occam.data.problem_space.states.transitions.TransitionType;

public abstract class ConceptTransition implements IConceptTransition {

	private final IConceptTransitionIC inputConfig;
	private final IConceptTransitionOIC outputInternConfig;
	private Salience salience;

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
		sB.append(inputConfig.getInputSymbol().toString())
			.append(", ")
			.append(inputConfig.getStackSymbol().toString())
			.append(" → ");
		Iterator<AVariable> varIte = outputInternConfig.getPushedStackSymbols().iterator();
		while (varIte.hasNext()) {
			sB.append(varIte.next());
			if (varIte.hasNext())
				sB.append(", ");
		}
		return sB.toString();
	}

	@Override
	abstract public TransitionType type();

}
