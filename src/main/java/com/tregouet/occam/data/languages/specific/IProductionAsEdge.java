package com.tregouet.occam.data.languages.specific;

import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.denotations.IDenotationSet;

public interface IProductionAsEdge extends IBasicProduction {
	
	@Override
	boolean equals(Object o);
	
	IDenotationSet getGenusDenotationSet();
	
	String getLabel();
	
	IDenotation getSource();
	
	IDenotationSet getSourceDenotationSet();
	
	IDenotationSet getSpeciesDenotationSet();
	
	IDenotation getTarget();
	
	IDenotationSet getTargetDenotationSet();
	
	@Override
	int hashCode();
	
	boolean isBlank();
	
	String toString();

}
