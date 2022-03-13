package com.tregouet.occam.data.automata.transitions.output_config;

import java.util.List;

import com.tregouet.occam.data.alphabets.ISymbol;

public interface IPushdownAutomatonOIC<
	StackSymbol extends ISymbol> 
	extends IOutputInternConfiguration {
	
	List<StackSymbol> getOutputStackSymbols();
	
	@Override
	boolean equals(Object o);
	
	@Override
	int hashCode();

}
