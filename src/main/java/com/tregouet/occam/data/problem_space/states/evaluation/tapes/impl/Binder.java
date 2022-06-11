package com.tregouet.occam.data.problem_space.states.evaluation.tapes.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.tregouet.occam.data.logical_structures.lambda_terms.IBindings;
import com.tregouet.occam.data.problem_space.states.evaluation.tapes.IBinder;
import com.tregouet.occam.data.problem_space.states.transitions.impl.stack_default.NothingBinding;

public class Binder implements IBinder {

	private List<IBindings> dimensionStack;

	public Binder() {
		dimensionStack = new ArrayList<>();
		dimensionStack.add(NothingBinding.INSTANCE);
	}

	public Binder(List<IBindings> dimensionStack) {
		this.dimensionStack = dimensionStack;
	}

	@Override
	public IBinder copy() {
		return new Binder(new ArrayList<>(dimensionStack));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Binder other = (Binder) obj;
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
	public IBindings popOff() {
		if (hasNext())
			return dimensionStack.remove(dimensionStack.size() - 1);
		return null;
	}

	@Override
	public void pushDown(IBindings symbol) {
		dimensionStack.add(symbol);
	}

}
