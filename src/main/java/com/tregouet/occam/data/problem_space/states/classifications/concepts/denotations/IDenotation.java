package com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations;

import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.logical_structures.partial_order.PartiallyComparable;

public interface IDenotation extends IConstruct {

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
