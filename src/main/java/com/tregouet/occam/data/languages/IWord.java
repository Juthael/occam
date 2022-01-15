package com.tregouet.occam.data.languages;

import java.util.Iterator;
import java.util.List;

public interface IWord<S extends ISymbol> extends Iterator<S> {
	
	List<S> getListOfSymbols();
	
	boolean hasNext();
	
	S next();
	
	void initializeSymbolIterator();
	
	boolean appendSymbol(S symbol);

}
