package com.tregouet.occam.data.concepts;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.transition_function.IState;
import com.tregouet.tree_finder.algo.unidimensional_sorting.IDichotomizable;

public interface IConcept extends IDichotomizable<IConcept> {
	
	public static final int ABSURDITY = 0;
	public static final int SINGLETON = 1;
	public static final int SUBSET_CONCEPT = 2;
	public static final int TRUISM = 3;
	public static final int ONTOLOGICAL_COMMITMENT = 4;
	
	@Override
	boolean equals(Object obj);
	
	Set<IContextObject> getExtent();
	
	int getID();
	
	Set<IIntentAttribute> getIntent();
	
	IIntentAttribute getMatchingAttribute(List<String> constraintAsStrings) throws IOException;
	
	@Override
	int hashCode();
	
	boolean meets(IConstruct constraint);
	
	boolean meets(List<String> constraintAsStrings);
	
	int rank();
	
	void setRank(int maxPathLengthFromMin);
	
	void setType(int type);
	
	@Override
	String toString();
	
	int type();
}
