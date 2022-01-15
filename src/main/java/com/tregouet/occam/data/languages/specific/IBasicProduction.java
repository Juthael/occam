package com.tregouet.occam.data.languages.specific;

import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.generic.IConstruct;

public interface IBasicProduction extends IProduction {
	
	@Override
	boolean equals(Object o);
	
	IConstruct getValue();
	
	@Override
	int hashCode();
	
	boolean isVariableSwitcher();
	
	@Override
	String toString();

}
