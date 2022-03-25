package com.tregouet.occam.data.automata.tapes;

import com.tregouet.occam.data.alphabets.ISymbol;

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
	
}
