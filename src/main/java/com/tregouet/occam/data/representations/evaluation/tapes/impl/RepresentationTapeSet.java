package com.tregouet.occam.data.representations.evaluation.tapes.impl;

import java.util.Objects;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.representations.evaluation.tapes.IFactTape;
import com.tregouet.occam.data.representations.evaluation.tapes.IRepresentationTapeSet;
import com.tregouet.occam.data.representations.evaluation.tapes.IVarBinder;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;

public class RepresentationTapeSet implements IRepresentationTapeSet {

	private IFactTape inputTape = null;
	private IVarBinder stack = null;

	public RepresentationTapeSet() {
		stack = new VarBinder();
	}

	public RepresentationTapeSet(IFactTape inputTape, IVarBinder stackTape) {
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
	public AVariable popOff() {
		return stack.popOff();
	}

	@Override
	public void printNext(IContextualizedProduction symbol) {
		inputTape.print(symbol);
	}

	@Override
	public void pushDown(AVariable stackSymbol) {
		stack.pushDown(stackSymbol);
	}

	@Override
	public IContextualizedProduction readNextInputSymbol() {
		return inputTape.read();
	}

}
