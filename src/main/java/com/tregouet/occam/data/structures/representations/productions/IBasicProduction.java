package com.tregouet.occam.data.structures.representations.productions;

public interface IBasicProduction extends IProduction {

	@Override
	boolean equals(Object o);

	@Override
	int hashCode();
	
	/**
	 * 
	 * @param prod
	 * @return null if not the same variable ; otherwise, compare values
	 */
	Integer compareTo(IBasicProduction prod);

}
