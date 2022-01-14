package com.tregouet.occam.data.denotations.impl;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.data.denotations.IComplementaryDenotationSet;
import com.tregouet.occam.data.denotations.IContextObject;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IDenotation;

public class ComplementaryDenotationSet extends AbstractDenotationSet implements IComplementaryDenotationSet {

	private final IDenotationSet complementedByThis;
	private IDenotationSet wrappedComplementing = null;
	private final Set<IContextObject> extent;
	private final int iD;
	
	public ComplementaryDenotationSet(IDenotationSet toBeComplemented, IDenotationSet complementing) {
		super();
		complementedByThis = toBeComplemented;
		wrappedComplementing = complementing;
		this.extent = new HashSet<>(Sets.difference(complementing.getExtent(), toBeComplemented.getExtent()));
		setType(IDenotationSet.CONTEXT_SUBSET);
		iD = wrappedComplementing.getID();
	}
	
	public ComplementaryDenotationSet(IDenotationSet toBeComplemented, Set<IContextObject> extent) {
		super();
		complementedByThis = toBeComplemented;
		this.extent = extent;
		setType(IDenotationSet.CONTEXT_SUBSET);
		iD = nextID++;
	}	
	
	@Override
	public IDenotationSet buildComplementOfThis(Set<IDenotationSet> rebutterMinimalLowerBounds) {
		return null;
	}	
	
	@Override
	public IDenotationSet complementThisWith(IDenotationSet complementing) {
		return null;
	}	
	
	@Override
	public IDenotationSet getComplemented() {
		return complementedByThis;
	}

	@Override
	public IDenotationSet getEmbeddedDenotationSet() {
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
