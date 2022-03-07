package com.tregouet.occam.data.denotations;

public interface IComplementaryPreconcept extends IPreconcept {
	
	IPreconcept getEmbeddedDenotationSet();
	
	boolean containsDenotations();

}
