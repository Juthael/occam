package com.tregouet.occam.data.representations.concepts.denotations;

import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.logical_structures.orders.partial.PartiallyComparable;

public interface IDenotation extends IConstruct, PartiallyComparable<IDenotation> {

	@Override
	public int hashCode();

	@Override
	boolean equals(Object o);

	int getConceptID();

	boolean isRedundant();

	void markAsRedundant();
	
	@Override
	String toString();

}
