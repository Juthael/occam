package com.tregouet.occam.data.logical_structures.automata.tapes;

import com.tregouet.occam.data.languages.alphabets.ISymbol;

public interface IInputTape<InputSymbol extends ISymbol> {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);
	
	IInputTape<InputSymbol> copy();
	
	/**
	 * If no next symbol, returns null
	 * @return
	 */
	InputSymbol read();
	
	void print(InputSymbol symbol);
	
	boolean hasNext();

}
