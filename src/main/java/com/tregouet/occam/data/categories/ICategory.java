package com.tregouet.occam.data.categories;

import java.util.Set;

import com.tregouet.occam.data.constructs.IContextObject;

public interface ICategory {
	
	public static final int ABSURDITY = 0;
	public static final int OBJECT = 1;
	public static final int SUBSET_CAT = 2;
	public static final int TRUISM_TRUISM = 3;
	public static final int TRUISM = 4;
	public static final int ONTOLOGICAL_COMMITMENT = 5;
	
	@Override
	boolean equals(Object obj);
	
	Set<IContextObject> getExtent();
	
	Set<IIntentAttribute> getIntent();
	
	@Override
	int hashCode();
	
	int rank();
	
	void setRank(int maxPathLengthFromMin);
	
	void setType(int type);
	
	int type();
	
	@Override
	String toString();

}
