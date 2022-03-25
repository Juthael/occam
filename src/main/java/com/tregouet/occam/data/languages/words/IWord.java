package com.tregouet.occam.data.languages.words;

import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.languages.alphabets.ISymbol;

public interface IWord<S extends ISymbol> extends Iterator<S> {
	
	List<S> getListOfSymbols();
	
	void initialize();
	
	void print(S symbol);
	
	IWord<S> copy();

}
