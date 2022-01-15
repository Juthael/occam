package com.tregouet.occam.data.languages.specific;

import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.generic.IConstruct;

public interface IBasicProduction extends IProduction{
	
	boolean derives(AVariable var);
	
	@Override
	boolean equals(Object o);
	
	IConstruct getValue();
	
	@Override
	int hashCode();
	
	boolean isVariableSwitcher();
	
	@Override
	String toString();
	
	ICompositeProduction compose(IBasicProduction basicComponent);

}
