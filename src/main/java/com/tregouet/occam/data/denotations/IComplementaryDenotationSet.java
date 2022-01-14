package com.tregouet.occam.data.denotations;

public interface IComplementaryDenotationSet extends IDenotationSet {
	
	IDenotationSet getEmbeddedDenotationSet();
	
	boolean containsDenotations();

}
