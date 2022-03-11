package com.tregouet.occam.data.preconcepts;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.tree_finder.algo.unidimensional_sorting.IDichotomizable;

public interface IPreconcept extends IDichotomizable<IPreconcept> {
	
	public static final int ABSURDITY = 0;
	public static final int OBJECT = 1;
	public static final int CONTEXT_SUBSET = 2;
	public static final int TRUISM = 3;
	public static final int ONTOLOGICAL_COMMITMENT = 4;
	
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
	
	void setType(int type);
	
	@Override
	String toString();
	
	int type();
	
	void markRedundantDenotations();
}
