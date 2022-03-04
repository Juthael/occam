package com.tregouet.occam.data.languages.specific;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IDenotation;

public interface IProductionAsEdge {
	
	IConcept getGenusDenotationSet();
	
	String getLabel();
	
	IDenotation getSource();
	
	IConcept getSourceConcept();
	
	IConcept getSpeciesDenotationSet();
	
	IDenotation getTarget();
	
	IConcept getTargetConcept();

}
