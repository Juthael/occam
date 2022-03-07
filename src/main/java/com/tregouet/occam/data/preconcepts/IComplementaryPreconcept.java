package com.tregouet.occam.data.preconcepts;

public interface IComplementaryPreconcept extends IPreconcept {
	
	IPreconcept getEmbeddedDenotationSet();
	
	boolean containsDenotations();

}
