package com.tregouet.occam.data.languages.specific;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IDenotation;

public interface IBasicProductionAsEdge extends IBasicProduction {
	
	@Override
	boolean equals(Object o);
	
	IConcept getGenusDenotationSet();
	
	String getLabel();
	
	IDenotation getSource();
	
	IConcept getSourceDenotationSet();
	
	IConcept getSpeciesDenotationSet();
	
	IDenotation getTarget();
	
	IConcept getTargetDenotationSet();
	
	@Override
	int hashCode();
	
	String toString();

}
