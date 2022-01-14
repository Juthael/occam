package com.tregouet.occam.data.languages;

import java.util.List;

public interface IWord<S extends ISymbol> {
	
	List<S> getListOfSymbols();

}
