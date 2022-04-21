package com.tregouet.occam.data.representations.transitions.productions;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;

public interface IProduction extends ISymbol {

	boolean derives(AVariable var);

	@Override
	boolean equals(Object o);

	IConstruct getValue();

	AVariable getVariable();

	@Override
	int hashCode();

	// Denotes an empty string of symbols. A symbols are productions, also denotes
	// that no variable is derived.
	boolean isEpsilon();

	@Override
	String toString();
	
	/**
	 * 
	 * @return true if left term and right term are the same
	 */
	boolean isBlank();

}
