package com.tregouet.occam.data.alphabets.productions;

import com.tregouet.occam.data.preconcepts.IDenotation;
import com.tregouet.occam.data.preconcepts.IPreconcept;

public interface Input extends IProduction {
	
	IPreconcept getGenus();
	
	String getLabel();
	
	IDenotation getSource();
	
	IPreconcept getSpecies();
	
	IDenotation getTarget();
	
	IProduction getUncontextualizedProduction();

}
