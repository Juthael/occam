package com.tregouet.occam.data.structures.representations.productions;

public interface IBasicProduction extends IProduction {

	/**
	 *
	 * @param prod
	 * @return null if not the same variable ; otherwise, compare values
	 */
	Integer compareTo(IBasicProduction prod);

	@Override
	boolean equals(Object o);

	@Override
	int hashCode();

}
