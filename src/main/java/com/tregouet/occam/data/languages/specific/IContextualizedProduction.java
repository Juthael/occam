package com.tregouet.occam.data.languages.specific;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IDenotation;

public interface IContextualizedProduction extends IProduction {
	
	IConcept getGenus();
	
	String getLabel();
	
	IDenotation getSource();
	
	IConcept getSpecies();
	
	IDenotation getTarget();
	
	IProduction getUncontextualizedProduction();

}
