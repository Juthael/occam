package com.tregouet.occam.data.automata.tapes;

import com.tregouet.occam.data.alphabets.ISymbol;

public interface IPushdownAutomatonTapeSet<
	InputSymbol extends ISymbol, 
	StackSymbol extends ISymbol, 
	TapeSet extends IPushdownAutomatonTapeSet<InputSymbol, StackSymbol, TapeSet>
	>
	extends ITapeSet<InputSymbol, TapeSet> {
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object o);
	
	StackSymbol popOff();
	
	void pushDown(StackSymbol stackSymbol);

}
