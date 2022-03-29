package com.tregouet.occam.data.logical_structures.automata.tapes;

import com.tregouet.occam.data.languages.alphabets.ISymbol;

public interface ITapeSet<
	InputSymbol extends ISymbol,
	TapeSet extends ITapeSet<InputSymbol, TapeSet>
	> {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);
	
	TapeSet copy();
	
	InputSymbol readNextInputSymbol();
	
	void printNext(InputSymbol symbol);
	
	boolean hasNextInputSymbol();
	
}
