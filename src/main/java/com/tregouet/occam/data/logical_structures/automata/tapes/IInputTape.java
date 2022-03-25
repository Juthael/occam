package com.tregouet.occam.data.logical_structures.automata.tapes;

import com.tregouet.occam.data.languages.alphabets.ISymbol;
import com.tregouet.occam.data.languages.words.IWord;

public interface IInputTape<InputSymbol extends ISymbol> extends IWord<InputSymbol> {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);
	
	IInputTape<InputSymbol> copy();
	
	InputSymbol read();
	
	void print(InputSymbol symbol);

}
