package com.tregouet.occam.data.logical_structures.automata.tapes;

import com.tregouet.occam.data.languages.alphabets.ISymbol;

public interface IStack<StackSymbol extends ISymbol> {
	
	void pushDown(StackSymbol symbol);
	
	StackSymbol popOff();
	
	IStack<StackSymbol> copy();
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object o);

}
