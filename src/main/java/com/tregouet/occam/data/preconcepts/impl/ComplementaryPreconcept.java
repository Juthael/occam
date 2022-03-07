package com.tregouet.occam.data.preconcepts.impl;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.data.preconcepts.IComplementaryPreconcept;
import com.tregouet.occam.data.preconcepts.IContextObject;
import com.tregouet.occam.data.preconcepts.IDenotation;
import com.tregouet.occam.data.preconcepts.IPreconcept;

public class ComplementaryPreconcept extends AbstractPreconcept implements IComplementaryPreconcept {

	private final IPreconcept complementedByThis;
	private IPreconcept wrappedComplementing = null;
	private final Set<IContextObject> extent;
	private final int iD;
	
	public ComplementaryPreconcept(IPreconcept toBeComplemented, IPreconcept complementing) {
		super();
		complementedByThis = toBeComplemented;
		wrappedComplementing = complementing;
		this.extent = new HashSet<>(Sets.difference(complementing.getExtent(), toBeComplemented.getExtent()));
		setType(IPreconcept.CONTEXT_SUBSET);
		iD = wrappedComplementing.getID();
	}
	
	public ComplementaryPreconcept(IPreconcept toBeComplemented, Set<IContextObject> extent) {
		super();
		complementedByThis = toBeComplemented;
		this.extent = extent;
		setType(IPreconcept.CONTEXT_SUBSET);
		iD = nextID++;
	}	
	
	@Override
	public IPreconcept buildComplementOfThis(Set<IPreconcept> rebutterMinimalLowerBounds) {
		return null;
	}	
	
	@Override
	public IPreconcept complementThisWith(IPreconcept complementing) {
		return null;
	}	
	
	@Override
	public IPreconcept getComplemented() {
		return complementedByThis;
	}

	@Override
	public IPreconcept getEmbeddedDenotationSet() {
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
	public Set<IDenotation> getDenotations() {
		if (wrappedComplementing == null)
			return new HashSet<>();
		else return wrappedComplementing.getDenotations();
	}

	@Override
	public boolean containsDenotations() {
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
