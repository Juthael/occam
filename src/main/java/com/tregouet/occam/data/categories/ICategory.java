package com.tregouet.occam.data.categories;

import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.exceptions.PropertyTargetingException;

public interface ICategory {
	
	public static final int ABSURDITY = 0;
	public static final int OBJECT = 1;
	public static final int SUBSET_CAT = 2;
	public static final int TRUISM = 3;
	public static final int ONTOLOGICAL_COMMITMENT = 4;
	
	@Override
	boolean equals(Object obj);
	
	Set<IContextObject> getExtent();
	
	Set<IIntentAttribute> getIntent();
	
	@Override
	int hashCode();
	
	int rank();
	
	void setRank(int maxPathLengthFromMin);
	
	void setType(int type);
	
	@Override
	String toString();
	
	int type();
	
	boolean meets(List<String> constraintAsStrings);
	
	boolean meets(IConstruct constraint);
	
	IIntentAttribute getMatchingAttribute(List<String> constraintAsStrings) throws PropertyTargetingException;
	
	int getID();

}
