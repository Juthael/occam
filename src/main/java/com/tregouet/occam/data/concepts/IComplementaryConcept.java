package com.tregouet.occam.data.concepts;

public interface IComplementaryConcept extends IConcept {
	
	IConcept getEmbeddedConcept();
	
	boolean hasAnIntent();

}
