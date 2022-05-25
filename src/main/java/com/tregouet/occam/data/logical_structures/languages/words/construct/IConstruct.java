package com.tregouet.occam.data.logical_structures.languages.words.construct;

import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ITerminal;
import com.tregouet.occam.data.logical_structures.languages.words.IWord;

public interface IConstruct extends IWord<ISymbol> {

	public static final String EMPTY_CONSTRUCT_SYMBOL = "ε";

	@Override
	public IConstruct copy();

	public String getFunctionType();

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
	
	@Override
	String toString();
	
	boolean isAlphaConvertibleWith(IConstruct other);
}
