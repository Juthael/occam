package com.tregouet.occam.data.logical_structures.automata.tapes;

import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;

public interface IStack<StackSymbol extends ISymbol> {
	
	void pushDown(StackSymbol symbol);
	
	/**
	 * If no next symbol, returns null
	 * @return
	 */
	StackSymbol popOff();
	
	IStack<StackSymbol> copy();
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object o);
	
	boolean hasNext();

}
