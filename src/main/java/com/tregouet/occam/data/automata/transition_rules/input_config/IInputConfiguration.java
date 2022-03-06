package com.tregouet.occam.data.automata.transition_rules.input_config;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.automata.states.IState;

public interface IInputConfiguration<I extends ISymbol> {
	
	IState getInputState();
	
	I getInputSymbol();
	
	@Override
	boolean equals(Object o);
	
	@Override
	int hashCode();

}
