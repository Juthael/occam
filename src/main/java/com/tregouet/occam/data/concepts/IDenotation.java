package com.tregouet.occam.data.concepts;

import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.partial_order.PartiallyComparable;

public interface IDenotation extends IConstruct, PartiallyComparable<IDenotation> {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);
	
	IConcept getConcept();
	
	void markAsRedundant();
	
	boolean isRedundant();

}
