package com.tregouet.occam.data.automata.tapes;

import com.tregouet.occam.data.alphabets.ISymbol;

public interface IStackTape<StackSymbol extends ISymbol> {
	
	void pushDown(StackSymbol symbol);
	
	StackSymbol popOff();
	
	IStackTape<StackSymbol> clone();

}
