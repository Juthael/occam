package com.tregouet.occam.data.concepts.impl;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.constructs.IContextObject;

public class ComplementaryConcept extends AbstractConcept implements IConcept {

	private final IConcept complementedByThis;
	private IConcept wrappedComplementing = null;
	private final Set<IContextObject> extent;
	private final int iD;
	
	public ComplementaryConcept(IConcept toBeComplemented, Set<IContextObject> extent) {
		super();
		complementedByThis = toBeComplemented;
		this.extent = extent;
		setType(IConcept.SUBSET_CONCEPT);
		iD = nextID++;
	}
	
	public ComplementaryConcept(IConcept toBeComplemented, IConcept complementing) {
		super();
		complementedByThis = toBeComplemented;
		wrappedComplementing = complementing;
		this.extent = new HashSet<>(Sets.difference(complementing.getExtent(), toBeComplemented.getExtent()));
		setType(IConcept.SUBSET_CONCEPT);
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
	public IConcept complementThisWith(IConcept complementing) {
		return null;
	}

	@Override
	public IConcept buildComplementOfThis(Set<IConcept> rebutterMinimalLowerBounds) {
		return null;
	}

	@Override
	public IConcept getComplemented() {
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
