package com.tregouet.occam.data.automata.transition_rules.input_config;

import com.tregouet.occam.data.alphabets.ISymbol;

public interface IPushdownAutomatonIC<I extends ISymbol, S extends ISymbol> extends IInputConfiguration<I> {
	
	S getInputStackSymbol();

}
