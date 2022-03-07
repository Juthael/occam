package com.tregouet.occam.data.automata.transition_rules.input_config;

import com.tregouet.occam.data.alphabets.ISymbol;

public interface IPushdownAutomatonIC<Tape extends ISymbol, Stack extends ISymbol> extends IInputConfiguration<Tape> {
	
	Stack getInputStackSymbol();

}
