package com.tregouet.occam.data.concepts.impl;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.data.concepts.IComplementaryConcept;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIntentConstruct;
import com.tregouet.occam.data.languages.generic.IContextObject;

public class ComplementaryConcept extends AbstractConcept implements IComplementaryConcept {

	private final IConcept complementedByThis;
	private IConcept wrappedComplementing = null;
	private final Set<IContextObject> extent;
	private final int iD;
	
	public ComplementaryConcept(IConcept toBeComplemented, IConcept complementing) {
		super();
		complementedByThis = toBeComplemented;
		wrappedComplementing = complementing;
		this.extent = new HashSet<>(Sets.difference(complementing.getExtent(), toBeComplemented.getExtent()));
		setType(IConcept.SUBSET_CONCEPT);
		iD = wrappedComplementing.getID();
	}
	
	public ComplementaryConcept(IConcept toBeComplemented, Set<IContextObject> extent) {
		super();
		complementedByThis = toBeComplemented;
		this.extent = extent;
		setType(IConcept.SUBSET_CONCEPT);
		iD = nextID++;
	}	
	
	@Override
	public IConcept buildComplementOfThis(Set<IConcept> rebutterMinimalLowerBounds) {
		return null;
	}	
	
	@Override
	public IConcept complementThisWith(IConcept complementing) {
		return null;
	}	
	
	@Override
	public IConcept getComplemented() {
		return complementedByThis;
	}

	@Override
	public IConcept getEmbeddedConcept() {
		return wrappedComplementing;
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
	public Set<IIntentConstruct> getIntent() {
		if (wrappedComplementing == null)
			return new HashSet<>();
		else return wrappedComplementing.getIntent();
	}

	@Override
	public boolean hasAnIntent() {
		return (wrappedComplementing != null);
	}

	@Override
	public boolean isComplementary() {
		return true;
	}

	@Override
	public int rank() {
		return -1;
	}

	@Override
	public void setRank(int maxPathLengthFromMin) {
		// do nothing	
	}

	@Override
	public String toString() {
		return Integer.toString(-complementedByThis.getID());
	}	

}
