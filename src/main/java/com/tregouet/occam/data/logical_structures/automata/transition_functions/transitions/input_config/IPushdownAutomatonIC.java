package com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.input_config;

import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;

public interface IPushdownAutomatonIC<
	InputSymbol extends ISymbol, 
	StackSymbol extends ISymbol> 
	extends IInputConfiguration<InputSymbol> {
	
	StackSymbol getStackSymbol();
	
	@Override
	boolean equals(Object o);
	
	@Override
	int hashCode();

}
