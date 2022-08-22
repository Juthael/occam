package com.tregouet.occam.data.structures.automata.transition_functions.transitions.input_config;

import com.tregouet.occam.data.structures.languages.alphabets.ISymbol;

public interface IPushdownAutomatonIC<InputSymbol extends ISymbol, StackSymbol extends ISymbol>
		extends IInputConfiguration<InputSymbol> {

	@Override
	boolean equals(Object o);

	StackSymbol getStackSymbol();

	@Override
	int hashCode();

}
