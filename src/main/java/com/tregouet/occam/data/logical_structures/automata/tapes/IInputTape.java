package com.tregouet.occam.data.logical_structures.automata.tapes;

import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;

public interface IInputTape<InputSymbol extends ISymbol> {

	@Override
	public int hashCode();

	IInputTape<InputSymbol> copy();

	@Override
	boolean equals(Object o);

	boolean hasNext();

	void print(InputSymbol symbol);

	/**
	 * If no next symbol, returns null
	 *
	 * @return
	 */
	InputSymbol read();

}
