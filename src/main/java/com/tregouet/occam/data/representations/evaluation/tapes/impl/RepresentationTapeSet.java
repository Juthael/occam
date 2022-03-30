package com.tregouet.occam.data.representations.evaluation.tapes.impl;

import java.util.Objects;

import com.tregouet.occam.data.languages.alphabets.domain_specific.IContextualizedProduction;
import com.tregouet.occam.data.languages.alphabets.generic.AVariable;
import com.tregouet.occam.data.representations.evaluation.tapes.IFactTape;
import com.tregouet.occam.data.representations.evaluation.tapes.IRepresentationTapeSet;
import com.tregouet.occam.data.representations.evaluation.tapes.IVarBinder;

public class RepresentationTapeSet implements IRepresentationTapeSet {

	private IFactTape inputTape = null;
	private IVarBinder stack = null;
	
	public RepresentationTapeSet() {
		inputTape = new FactTape();
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
	public AVariable popOff() {
		return stack.popOff();
	}

	@Override
	public void pushDown(AVariable stackSymbol) {
		stack.pushDown(stackSymbol);
	}

	@Override
	public IContextualizedProduction readNextInputSymbol() {
		return inputTape.read();
	}

	@Override
	public void printNext(IContextualizedProduction symbol) {
		inputTape.print(symbol);
	}

	@Override
	public int hashCode() {
		return Objects.hash(inputTape, stack);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RepresentationTapeSet other = (RepresentationTapeSet) obj;
		return Objects.equals(inputTape, other.inputTape) && Objects.equals(stack, other.stack);
	}

	@Override
	public boolean hasNextInputSymbol() {
		return inputTape.hasNext();
	}

	@Override
	public IFactTape getInputTape() {
		return inputTape;
	}

}
