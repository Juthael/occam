package com.tregouet.occam.data.logical_structures.automata.tapes;

import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;

public interface ITapeSet<InputSymbol extends ISymbol, TapeSet extends ITapeSet<InputSymbol, TapeSet>> {

	TapeSet copy();

	@Override
	boolean equals(Object o);

	@Override
	public int hashCode();

	boolean hasNextInputSymbol();

	void printNext(InputSymbol symbol);

	InputSymbol readNextInputSymbol();

}
