package com.tregouet.occam.data.problem_space.states.evaluation.tapes.impl;

import java.util.Objects;

import com.tregouet.occam.data.logical_structures.lambda_terms.IBindings;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.problem_space.states.evaluation.tapes.IBinder;
import com.tregouet.occam.data.problem_space.states.evaluation.tapes.IFactTape;
import com.tregouet.occam.data.problem_space.states.evaluation.tapes.IRepresentationTapeSet;

public class RepresentationTapeSet implements IRepresentationTapeSet {

	private IFactTape inputTape = null;
	private IBinder stack = null;

	public RepresentationTapeSet() {
		inputTape = new FactTape();
		stack = new Binder();
	}

	public RepresentationTapeSet(IFactTape inputTape, IBinder stackTape) {
		this.inputTape = inputTape;
		this.stack = stackTape;
	}

	@Override
	public IRepresentationTapeSet copy() {
		return new RepresentationTapeSet(inputTape.copy(), stack.copy());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		RepresentationTapeSet other = (RepresentationTapeSet) obj;
		return Objects.equals(inputTape, other.inputTape) && Objects.equals(stack, other.stack);
	}

	@Override
	public IFactTape getInputTape() {
		return inputTape;
	}

	@Override
	public int hashCode() {
		return Objects.hash(inputTape, stack);
	}

	@Override
	public boolean hasNextInputSymbol() {
		return inputTape.hasNext();
	}

	@Override
	public void input(IFactTape inputTape) {
		this.inputTape = inputTape;
	}

	@Override
	public IBindings popOff() {
		return stack.popOff();
	}

	@Override
	public void printNext(IComputation symbol) {
		inputTape.print(symbol);
	}

	@Override
	public void pushDown(IBindings stackSymbol) {
		stack.pushDown(stackSymbol);
	}

	@Override
	public IComputation readNextInputSymbol() {
		return inputTape.read();
	}

}
