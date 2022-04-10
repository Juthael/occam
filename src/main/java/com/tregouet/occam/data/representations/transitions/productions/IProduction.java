package com.tregouet.occam.data.representations.transitions.productions;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;

public interface IProduction extends ISymbol {
	
	AVariable getVariable();
	
	boolean derives(AVariable var);
	
	@Override
	boolean equals(Object o);
	
	@Override
	int hashCode();
	
	@Override
	String toString();
	
	//Denotes an empty string of symbols. A symbols are productions, also denotes that no variable is derived. 
	boolean isEpsilon();
	
	IConstruct getValue();

}
