package com.tregouet.occam.data.automata.transition_functions.transitions.input_config;

import com.tregouet.occam.data.alphabets.ISymbol;

public interface IInputConfiguration<InputSymbol extends ISymbol> {
	
	int getInputStateID();
	
	InputSymbol getInputSymbol();
	
	@Override
	boolean equals(Object o);
	
	@Override
	int hashCode();

}
