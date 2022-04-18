package com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.output_config;

import java.util.List;

import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;

public interface IPushdownAutomatonOIC<StackSymbol extends ISymbol> extends IOutputInternConfiguration {

	@Override
	boolean equals(Object o);

	List<StackSymbol> getPushedStackSymbols();

	@Override
	int hashCode();

}
