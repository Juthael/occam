package com.tregouet.occam.data.logical_structures.languages.words;

import java.util.List;

import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;

public interface IWord<S extends ISymbol> {
	
	List<S> asList();
	
	IWord<S> copy();

}
