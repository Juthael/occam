package com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.output_config;

import java.util.List;

import com.tregouet.occam.data.languages.alphabets.ISymbol;

public interface IPushdownAutomatonOIC<
	StackSymbol extends ISymbol> 
	extends IOutputInternConfiguration {
	
	List<StackSymbol> getPushedStackSymbols();
	
	@Override
	boolean equals(Object o);
	
	@Override
	int hashCode();

}
