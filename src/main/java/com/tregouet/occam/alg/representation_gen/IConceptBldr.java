package com.tregouet.occam.alg.representation_gen;

import com.tregouet.occam.data.preconcepts.impl.Preconcept;
import com.tregouet.occam.data.representations.IConcept;

public interface IConceptBldr {
	
	IConceptBldr input(Preconcept preconcept);
	
	IConcept output();

}
