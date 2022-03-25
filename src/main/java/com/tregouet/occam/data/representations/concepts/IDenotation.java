package com.tregouet.occam.data.representations.concepts;

import com.tregouet.occam.data.languages.words.construct.IConstruct;
import com.tregouet.occam.data.logical_structures.partial_order.PartiallyComparable;

public interface IDenotation extends IConstruct, PartiallyComparable<IDenotation> {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);
	
	IConcept getConcept();
	
	void markAsRedundant();
	
	boolean isRedundant();

}
