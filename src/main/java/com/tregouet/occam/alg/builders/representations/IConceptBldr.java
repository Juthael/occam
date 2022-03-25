package com.tregouet.occam.alg.builders.representations;

import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.impl.Concept;

public interface IConceptBldr {
	
	IConceptBldr input(Concept concept);
	
	IConcept output();

}
