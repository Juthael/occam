package com.tregouet.occam.data.automata.tapes;

import com.tregouet.occam.data.alphabets.ISymbol;

public interface IStack<StackSymbol extends ISymbol> {
	
	void pushDown(StackSymbol symbol);
	
	StackSymbol popOff();
	
	IStack<StackSymbol> copy();

}
