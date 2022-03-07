package com.tregouet.occam.data.alphabets.productions;

import com.tregouet.occam.data.denotations.IPreconcept;
import com.tregouet.occam.data.denotations.IDenotation;

public interface IContextualizedProduction extends IProduction {
	
	IPreconcept getGenus();
	
	String getLabel();
	
	IDenotation getSource();
	
	IPreconcept getSpecies();
	
	IDenotation getTarget();
	
	IProduction getUncontextualizedProduction();

}
