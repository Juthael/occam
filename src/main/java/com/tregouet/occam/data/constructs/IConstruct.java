package com.tregouet.occam.data.constructs;

import java.util.Iterator;
import java.util.List;

public interface IConstruct {
	
	Iterator<ISymbol> getIteratorOverSymbols();
	
	List<ISymbol> getListOfSymbols();
	
	int getNbOfTerminals();
	
	boolean isAbstract();
	
	boolean meets(IConstruct constraint);
	
	void singularize();
	
	List<String> toListOfStringsWithPlaceholders();

}
