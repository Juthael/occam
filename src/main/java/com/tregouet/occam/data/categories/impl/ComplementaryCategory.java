package com.tregouet.occam.data.categories.impl;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.constructs.IContextObject;

public class ComplementaryCategory extends AbstractCategory implements ICategory {

	private final ICategory complementedByThis;
	private ICategory wrappedComplementing = null;
	private final Set<IContextObject> extent;
	private final int iD;
	
	public ComplementaryCategory(ICategory toBeComplemented, Set<IContextObject> extent) {
		super();
		complementedByThis = toBeComplemented;
		this.extent = extent;
		setType(ICategory.SUBSET_CAT);
		iD = nextID++;
	}
	
	public ComplementaryCategory(ICategory toBeComplemented, ICategory complementing) {
		super();
		complementedByThis = toBeComplemented;
		wrappedComplementing = complementing;
		this.extent = new HashSet<>(Sets.difference(complementing.getExtent(), toBeComplemented.getExtent()));
		setType(ICategory.SUBSET_CAT);
		iD = wrappedComplementing.getID();
	}	
	
	@Override
	public String toString() {
		return Integer.toString(-complementedByThis.getID());
	}	
	
	@Override
	public boolean isComplementary() {
		return true;
	}	
	
	@Override
	public ICategory complementThisWith(ICategory complementing) {
		return null;
	}

	@Override
	public ICategory buildComplementOfThis(Set<ICategory> rebutterMinimalLowerBounds) {
		return null;
	}

	@Override
	public ICategory getComplemented() {
		return complementedByThis;
	}

	@Override
	public Set<IContextObject> getExtent() {
		return extent;
	}

	@Override
	public int getID() {
		return iD;
	}

	@Override
	public Set<IIntentAttribute> getIntent() {
		if (wrappedComplementing == null)
			return new HashSet<>();
		else return wrappedComplementing.getIntent();
	}

	@Override
	public int rank() {
		return -1;
	}

	@Override
	public void setRank(int maxPathLengthFromMin) {
		// do nothing	
	}	

}
