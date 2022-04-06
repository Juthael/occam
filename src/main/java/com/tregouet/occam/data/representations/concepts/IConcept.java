package com.tregouet.occam.data.representations.concepts;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.languages.words.construct.IConstruct;
import com.tregouet.occam.data.logical_structures.automata.states.IState;
import com.tregouet.tree_finder.algo.unidimensional_sorting.IDichotomizable;

public interface IConcept extends IDichotomizable<IConcept>, IState {
	
	public static final int WHAT_IS_THERE_ID = 9999;
	
	@Override
	boolean equals(Object obj);
	
	Set<IContextObject> getExtent();
	
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
	
	void initializeIDGenerator();
}
