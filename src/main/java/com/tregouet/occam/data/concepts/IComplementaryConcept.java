package com.tregouet.occam.data.concepts;

public interface IComplementaryConcept extends IConcept {
	
	boolean hasAnIntent();
	
	IConcept getEmbeddedConcept();

}
