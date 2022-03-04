package com.tregouet.occam.data.denotations;

public interface IComplementaryConcept extends IConcept {
	
	IConcept getEmbeddedDenotationSet();
	
	boolean containsDenotations();

}
