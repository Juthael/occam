package com.tregouet.occam.data.preconcepts;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.representations.concepts.ConceptType;
import com.tregouet.tree_finder.algo.unidimensional_sorting.IDichotomizable;

public interface IPreconcept extends IDichotomizable<IPreconcept> {
	
	@Override
	boolean equals(Object obj);
	
	Set<IContextObject> getExtent();
	
	int getID();
	
	Set<IDenotation> getDenotations();
	
	Set<IDenotation> getRedundantDenotations();
	
	IDenotation getMatchingDenotation(List<String> constraintAsStrings) throws IOException;
	
	@Override
	int hashCode();
	
	boolean meets(IConstruct constraint);
	
	boolean meets(List<String> constraintAsStrings);
	
	void setType(ConceptType type);
	
	@Override
	String toString();
	
	ConceptType type();
}
