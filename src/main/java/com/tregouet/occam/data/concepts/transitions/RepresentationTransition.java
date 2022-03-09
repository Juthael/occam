package com.tregouet.occam.data.concepts.transitions;

import java.util.Objects;

import com.tregouet.occam.data.alphabets.productions.impl.ContextualizedProduction;
import com.tregouet.occam.data.automata.transitions.IPushdownAutomatonTransition;
import com.tregouet.occam.data.automata.transitions.ITransition;
import com.tregouet.occam.data.languages.generic.AVariable;

public class RepresentationTransition implements IPushdownAutomatonTransition<ContextualizedProduction, AVariable> {

	private final String name;
	private final RepresentationTransitionIC inputConfig;
	private final RepresentationTransitionOIC outputInternConfig;
	
	public RepresentationTransition(RepresentationTransitionIC inputConfig, RepresentationTransitionOIC outputInternConfig) {
		name = ITransition.provideName();
		this.inputConfig = inputConfig;
		this.outputInternConfig = outputInternConfig;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public RepresentationTransitionIC getInputConfiguration() {
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
		RepresentationTransition other = (RepresentationTransition) obj;
		return Objects.equals(inputConfig, other.inputConfig) && Objects.equals(name, other.name)
				&& Objects.equals(outputInternConfig, other.outputInternConfig);
	}

	@Override
	public RepresentationTransitionOIC getOutputInternConfiguration() {
		return outputInternConfig;
	}

}
