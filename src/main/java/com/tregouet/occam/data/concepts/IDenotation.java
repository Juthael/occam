package com.tregouet.occam.data.concepts;

import com.tregouet.occam.data.languages.generic.IConstruct;

public interface IDenotation extends IConstruct {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);
	
	IConcept getDenotationSet();

}
