package com.tregouet.occam.data.automata.transitions.input_config;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.automata.states.IState;

public interface IInputConfiguration<InputSymbol extends ISymbol> {
	
	IState getInputState();
	
	InputSymbol getInputSymbol();
	
	@Override
	boolean equals(Object o);
	
	@Override
	int hashCode();

}
