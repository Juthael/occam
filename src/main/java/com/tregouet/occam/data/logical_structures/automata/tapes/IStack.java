package com.tregouet.occam.data.logical_structures.automata.tapes;

import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;

public interface IStack<StackSymbol extends ISymbol> {

	IStack<StackSymbol> copy();

	@Override
	boolean equals(Object o);

	@Override
	int hashCode();

	boolean hasNext();

	/**
	 * If no next symbol, returns null
	 * @return
	 */
	StackSymbol popOff();

	void pushDown(StackSymbol symbol);

}
