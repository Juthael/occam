package com.tregouet.occam.alg.builders.representations;

import com.tregouet.occam.data.concepts.impl.Concept;
import com.tregouet.occam.data.representations.concepts.IConcept;

public interface IConceptBldr {
	
	IConceptBldr input(Concept concept);
	
	IConcept output();

}
