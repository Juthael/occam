package com.tregouet.occam.data.problem_space.states.transitions.impl;

import java.util.Objects;

import com.tregouet.occam.data.logical_structures.lambda_terms.IBindings;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.applications.IAbstractionApplication;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransitionIC;

public class ConceptTransitionIC implements IConceptTransitionIC {

	private final int inputStateID;
	private final IAbstractionApplication inputSymbol;
	private final IBindings stackSymbol;

	public ConceptTransitionIC(int inputStateID, IAbstractionApplication inputSymbol, IBindings stackSymbol) {
		this.inputStateID = inputStateID;
		this.inputSymbol = inputSymbol;
		this.stackSymbol = stackSymbol;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		ConceptTransitionIC other = (ConceptTransitionIC) obj;
		return inputStateID == other.inputStateID
				&& Objects.equals(inputSymbol, other.inputSymbol)
				&& Objects.equals(stackSymbol, other.stackSymbol);
	}

	@Override
	public int getInputStateID() {
		return inputStateID;
	}

	@Override
	public IAbstractionApplication getInputSymbol() {
		return inputSymbol;
	}

	@Override
	public IBindings getStackSymbol() {
		return stackSymbol;
	}

	@Override
	public int hashCode() {
		return Objects.hash(inputStateID, inputSymbol, stackSymbol);
	}

}
