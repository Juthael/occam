package com.tregouet.occam.data.languages.generic;

import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.languages.IWord;

public interface IConstruct extends IWord<ISymbol> {
	
	public static final String EMPTY_CONSTRUCT_SYMBOL = "ε";
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);
	
	Iterator<ISymbol> getIteratorOverSymbols();
	
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
