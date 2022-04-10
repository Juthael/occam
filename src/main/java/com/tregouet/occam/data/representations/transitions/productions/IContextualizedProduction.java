package com.tregouet.occam.data.representations.transitions.productions;

import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.denotations.IDenotation;

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
