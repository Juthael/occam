package com.tregouet.occam.data.concepts.transitions.impl;

import java.util.Objects;

import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.automata.transitions.input_config.impl.InputConfiguration;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.languages.generic.AVariable;

public class ConceptTransitionIC extends InputConfiguration<IContextualizedProduction> 
	implements IConceptTransitionIC {

	private final AVariable stackSymbol;
	
	public ConceptTransitionIC(IConcept inputState, IContextualizedProduction inputSymbol, AVariable stackSymbol) {
		super(inputState, inputSymbol);
		this.stackSymbol = stackSymbol;
	}

	@Override
	public AVariable getInputStackSymbol() {
		return stackSymbol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(stackSymbol);
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
		ConceptTransitionIC other = (ConceptTransitionIC) obj;
		return Objects.equals(stackSymbol, other.stackSymbol);
	}

}