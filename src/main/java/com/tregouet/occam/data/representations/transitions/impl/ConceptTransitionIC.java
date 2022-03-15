package com.tregouet.occam.data.representations.transitions.impl;

import java.util.Objects;

import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.representations.IConcept;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionIC;

public class ConceptTransitionIC implements IConceptTransitionIC {

	private final IConcept inputState;
	private final IContextualizedProduction inputSymbol;
	private final AVariable stackSymbol;
	
	public ConceptTransitionIC(IConcept inputState, IContextualizedProduction inputSymbol, AVariable stackSymbol) {
		this.inputState = inputState;
		this.inputSymbol = inputSymbol;
		this.stackSymbol = stackSymbol;
	}

	@Override
	public AVariable getInputStackSymbol() {
		return stackSymbol;
	}

	@Override
	public IState getInputState() {
		return inputState;
	}

	@Override
	public IContextualizedProduction getInputSymbol() {
		return inputSymbol;
	}

	@Override
	public int hashCode() {
		return Objects.hash(inputState, inputSymbol, stackSymbol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConceptTransitionIC other = (ConceptTransitionIC) obj;
		return Objects.equals(inputState, other.inputState) && Objects.equals(inputSymbol, other.inputSymbol)
				&& Objects.equals(stackSymbol, other.stackSymbol);
	}

}
