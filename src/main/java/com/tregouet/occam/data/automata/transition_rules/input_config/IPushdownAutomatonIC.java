package com.tregouet.occam.data.automata.transition_rules.input_config;

import com.tregouet.occam.data.alphabets.ISymbol;

public interface IPushdownAutomatonIC<InputSymbol extends ISymbol, StackSymbol extends ISymbol> extends IInputConfiguration<InputSymbol> {
	
	StackSymbol getInputStackSymbol();

}
