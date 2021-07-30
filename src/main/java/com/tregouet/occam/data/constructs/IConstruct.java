package com.tregouet.occam.data.constructs;

import java.util.Iterator;
import java.util.List;

public interface IConstruct {
	
	public int hashCode();
	
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
