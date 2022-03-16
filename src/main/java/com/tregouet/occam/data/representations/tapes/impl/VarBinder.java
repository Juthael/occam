package com.tregouet.occam.data.representations.tapes.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.representations.tapes.IVarBinder;
import com.tregouet.occam.data.representations.transitions.dimensions.Nothing;

public class VarBinder implements IVarBinder {

	List<AVariable> dimensionStack;
	
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

}
