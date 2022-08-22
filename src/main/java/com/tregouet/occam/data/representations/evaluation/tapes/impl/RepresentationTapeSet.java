package com.tregouet.occam.data.representations.evaluation.tapes.impl;

import java.util.Objects;

import com.tregouet.occam.data.representations.descriptions.differentiae.properties.computations.abstr_app.IAbstractionApplication;
import com.tregouet.occam.data.representations.evaluation.tapes.IBinder;
import com.tregouet.occam.data.representations.evaluation.tapes.IFactTape;
import com.tregouet.occam.data.representations.evaluation.tapes.IRepresentationTapeSet;
import com.tregouet.occam.data.structures.lambda_terms.IBindings;

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
	public void printNext(IAbstractionApplication symbol) {
		inputTape.print(symbol);
	}

	@Override
	public void pushDown(IBindings stackSymbol) {
		stack.pushDown(stackSymbol);
	}

	@Override
	public IAbstractionApplication readNextInputSymbol() {
		return inputTape.read();
	}

}
