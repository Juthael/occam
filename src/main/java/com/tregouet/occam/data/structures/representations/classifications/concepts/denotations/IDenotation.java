package com.tregouet.occam.data.structures.representations.classifications.concepts.denotations;

import com.tregouet.occam.data.structures.languages.words.construct.IConstruct;

public interface IDenotation extends IConstruct {

	@Override
	public int hashCode();

	@Override
	boolean equals(Object o);

	int getConceptID();

	boolean isArbitraryLabel();

	boolean isRedundant();

	void markAsRedundant();

	@Override
	String toString();

}
