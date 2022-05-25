package com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.input_config;

import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;

public interface IInputConfiguration<InputSymbol extends ISymbol> {

	@Override
	boolean equals(Object o);

	int getInputStateID();

	InputSymbol getInputSymbol();

	@Override
	int hashCode();

}
