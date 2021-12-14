package com.tregouet.occam.data.concepts;

import com.tregouet.occam.data.languages.generic.IConstruct;

public interface IIntentConstruct extends IConstruct {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);
	
	IConcept getConcept();

}
