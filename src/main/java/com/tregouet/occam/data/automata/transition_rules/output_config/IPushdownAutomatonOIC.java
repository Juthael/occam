package com.tregouet.occam.data.automata.transition_rules.output_config;

import com.tregouet.occam.data.alphabets.ISymbol;

public interface IPushdownAutomatonOIC<StackSymbol extends ISymbol> extends IOutputInternConfiguration {
	
	StackSymbol getOutputStackSymbol();

}
