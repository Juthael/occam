package com.tregouet.occam.data.automata.tapes;

import java.util.List;

import com.tregouet.occam.data.alphabets.ISymbol;

public interface IInputTape<InputSymbol extends ISymbol> {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);
	
	List<InputSymbol> getInputTape();
	
	IInputTape<InputSymbol> clone();
	
	boolean hasNext();
	
	InputSymbol readNext();

}
