package com.tregouet.occam.data.languages.generic;

import java.util.Iterator;
import java.util.List;

public interface IConstruct {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);
	
	Iterator<ISymbol> getIteratorOverSymbols();
	
	List<ISymbol> getListOfSymbols();
	
	List<ITerminal> getListOfTerminals();
	
	int getNbOfTerminals();
	
	List<AVariable> getVariables();
	
	boolean isAbstract();
	
	/**
	 * 
	 * @param constraint a construct that is not asbtract
	 * @return
	 */
	boolean meets(IConstruct constraint);
	
	void nameVariables();
	
	List<String> toListOfStringsWithPlaceholders();	
}
