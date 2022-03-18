package com.tregouet.occam.data.automata.transition_functions.transitions.input_config;

import com.tregouet.occam.data.alphabets.ISymbol;

public interface IPushdownAutomatonIC<
	InputSymbol extends ISymbol, 
	StackSymbol extends ISymbol> 
	extends IInputConfiguration<InputSymbol> {
	
	StackSymbol getInputStackSymbol();
	
	@Override
	boolean equals(Object o);
	
	@Override
	int hashCode();

}
