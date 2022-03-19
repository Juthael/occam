package com.tregouet.occam.data.automata.tapes;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.languages.IWord;

public interface IInputTape<InputSymbol extends ISymbol> extends IWord<InputSymbol> {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);
	
	IInputTape<InputSymbol> copy();
	
	InputSymbol read();
	
	void print(InputSymbol symbol);

}
