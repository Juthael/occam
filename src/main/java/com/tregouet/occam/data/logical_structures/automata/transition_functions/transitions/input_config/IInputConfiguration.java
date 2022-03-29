package com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.input_config;

import com.tregouet.occam.data.languages.alphabets.ISymbol;

public interface IInputConfiguration<InputSymbol extends ISymbol> {
	
	int getRequiredInputStateID();
	
	InputSymbol getInputSymbol();
	
	@Override
	boolean equals(Object o);
	
	@Override
	int hashCode();

}
