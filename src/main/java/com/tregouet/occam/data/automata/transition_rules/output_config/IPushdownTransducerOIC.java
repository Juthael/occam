package com.tregouet.occam.data.automata.transition_rules.output_config;

import com.tregouet.occam.data.alphabets.ISymbol;

public interface IPushdownTransducerOIC<S extends ISymbol, O extends ISymbol> extends IPushdownAutomatonOIC<S> {
	
	O getOutputSymbol();

}
