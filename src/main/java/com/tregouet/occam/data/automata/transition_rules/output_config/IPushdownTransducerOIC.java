package com.tregouet.occam.data.automata.transition_rules.output_config;

import com.tregouet.occam.data.alphabets.ISymbol;

public interface IPushdownTransducerOIC<StackSymbol extends ISymbol, Print extends ISymbol> extends IPushdownAutomatonOIC<StackSymbol> {
	
	Print getOutputSymbol();

}
