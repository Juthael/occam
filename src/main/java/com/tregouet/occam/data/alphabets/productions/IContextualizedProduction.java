package com.tregouet.occam.data.alphabets.productions;

import com.tregouet.occam.data.concepts.IDenotation;
import com.tregouet.occam.data.concepts.IConcept;

public interface IContextualizedProduction extends IProduction {
	
	IConcept getGenus();
	
	String getLabel();
	
	IDenotation getSource();
	
	IConcept getSpecies();
	
	IDenotation getTarget();
	
	IProduction getUncontextualizedProduction();
	
	/**
	 * 
	 * @return true if getSource().isRedundant(), false otherwise
	 */
	boolean isRedundant();
	
	@Override
	boolean equals(Object o);
	
	@Override
	int hashCode();

}
