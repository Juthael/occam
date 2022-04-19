package com.tregouet.occam.data.representations.transitions.productions;

import com.tregouet.occam.data.representations.concepts.denotations.IDenotation;

public interface IContextualizedProduction extends IProduction {

	@Override
	boolean equals(Object o);

	int getGenusID();

	String getLabel();

	IDenotation getSource();

	int getSpeciesID();

	IDenotation getTarget();

	IProduction getUncontextualizedProduction();

	@Override
	int hashCode();

	/**
	 *
	 * @return true if getSource().isRedundant(), false otherwise
	 */
	boolean isRedundant();

}
