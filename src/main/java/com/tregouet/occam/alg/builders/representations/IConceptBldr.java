package com.tregouet.occam.alg.builders.representations;

import com.tregouet.occam.data.preconcepts.impl.Preconcept;
import com.tregouet.occam.data.representations.concepts.IConcept;

public interface IConceptBldr {
	
	IConceptBldr input(Preconcept preconcept);
	
	IConcept output();

}
