package com.tregouet.occam.data.automata.transitions.output_config;

import com.tregouet.occam.data.alphabets.ISymbol;

public interface IPushdownTransducerOIC<StackSymbol extends ISymbol, OutputSymbol extends ISymbol> extends IPushdownAutomatonOIC<StackSymbol> {
	
	OutputSymbol getOutputSymbol();

}
