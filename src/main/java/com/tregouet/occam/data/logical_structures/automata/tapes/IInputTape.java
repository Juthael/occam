package com.tregouet.occam.data.logical_structures.automata.tapes;

import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;

public interface IInputTape<InputSymbol extends ISymbol> {

	IInputTape<InputSymbol> copy();

	@Override
	boolean equals(Object o);

	@Override
	public int hashCode();

	boolean hasNext();

	void print(InputSymbol symbol);

	/**
	 * If no next symbol, returns null
	 * 
	 * @return
	 */
	InputSymbol read();

}
