package com.tregouet.occam.data.structures.automata.tapes;

import com.tregouet.occam.data.structures.languages.alphabets.ISymbol;

public interface ITapeSet<InputSymbol extends ISymbol, TapeSet extends ITapeSet<InputSymbol, TapeSet>> {

	@Override
	public int hashCode();

	TapeSet copy();

	@Override
	boolean equals(Object o);

	boolean hasNextInputSymbol();

	void printNext(InputSymbol symbol);

	InputSymbol readNextInputSymbol();

}
