package com.tregouet.occam.data.representations.evaluation.tapes.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.tregouet.occam.data.languages.alphabets.generic.AVariable;
import com.tregouet.occam.data.representations.evaluation.tapes.IVarBinder;
import com.tregouet.occam.data.representations.properties.transitions.dimensions.Nothing;

public class VarBinder implements IVarBinder {

	private List<AVariable> dimensionStack;
	
	public VarBinder(List<AVariable> dimensionStack) {
		this.dimensionStack = dimensionStack;
	}
	
	public VarBinder() {
		dimensionStack = new ArrayList<>();
		dimensionStack.add(Nothing.INSTANCE);
	}
	
	@Override
	public void pushDown(AVariable symbol) {
		dimensionStack.add(symbol);
	}

	@Override
	public AVariable popOff() {
		if (!dimensionStack.isEmpty())
			return dimensionStack.remove(dimensionStack.size() - 1);
		return null;
	}

	@Override
	public IVarBinder copy() {
		return new VarBinder(new ArrayList<>(dimensionStack));
	}

	@Override
	public int hashCode() {
		return Objects.hash(dimensionStack);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VarBinder other = (VarBinder) obj;
		return Objects.equals(dimensionStack, other.dimensionStack);
	}

}
