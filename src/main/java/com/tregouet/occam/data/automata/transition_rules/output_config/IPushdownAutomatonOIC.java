package com.tregouet.occam.data.automata.transition_rules.output_config;

import com.tregouet.occam.data.alphabets.ISymbol;

public interface IPushdownAutomatonOIC<Stack extends ISymbol> extends IOutputInternConfiguration {
	
	Stack getOutputStackSymbol();

}
