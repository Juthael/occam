package com.tregouet.occam.data.problem_space.states.evaluation.tapes.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.evaluation.tapes.IVarBinder;
import com.tregouet.occam.data.problem_space.states.transitions.dimensions.Nothing;

public class VarBinder implements IVarBinder {

	private List<AVariable> dimensionStack;

	public VarBinder() {
		dimensionStack = new ArrayList<>();
		dimensionStack.add(Nothing.INSTANCE);
	}

	public VarBinder(List<AVariable> dimensionStack) {
		this.dimensionStack = dimensionStack;
	}

	@Override
	public IVarBinder copy() {
		return new VarBinder(new ArrayList<>(dimensionStack));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		VarBinder other = (VarBinder) obj;
		return Objects.equals(dimensionStack, other.dimensionStack);
	}

	@Override
	public int hashCode() {
		return Objects.hash(dimensionStack);
	}

	@Override
	public boolean hasNext() {
		return !dimensionStack.isEmpty();
	}

	@Override
	public AVariable popOff() {
		if (hasNext())
			return dimensionStack.remove(dimensionStack.size() - 1);
		return null;
	}

	@Override
	public void pushDown(AVariable symbol) {
		dimensionStack.add(symbol);
	}

}
