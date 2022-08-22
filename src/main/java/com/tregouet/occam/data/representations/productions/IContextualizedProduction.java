package com.tregouet.occam.data.representations.productions;

import com.tregouet.occam.data.representations.classifications.concepts.denotations.IDenotation;

public interface IContextualizedProduction extends IProduction {

	@Override
	boolean equals(Object o);

	String getLabel();

	IDenotation getSource();

	int getSubordinateID();

	int getSuperordinateID();

	IDenotation getTarget();

	IBasicProduction getUncontextualizedProduction();

	@Override
	int hashCode();

	/**
	 *
	 * @return true if getSource().isRedundant(), false otherwise
	 */
	boolean isRedundant();

}
