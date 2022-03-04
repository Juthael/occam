package com.tregouet.occam.data.concepts;

public interface IComplementaryConcept extends IConcept {
	
	IConcept getEmbeddedDenotationSet();
	
	boolean containsDenotations();

}
