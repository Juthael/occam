package com.tregouet.occam.data.denotations.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.IContextObject;

public class DenotationSet extends AbstractDenotationSet implements IDenotationSet {

	private final Set<IDenotation> denotations = new HashSet<>();
	private final Set<IContextObject> extent;
	protected int rank = 0;
	private final int iD;
	
	public DenotationSet(Set<IConstruct> denotatingConstructs, Set<IContextObject> extent) {
		for (IConstruct construct : denotatingConstructs)
			this.denotations.add(new Denotation(construct, this));
		this.extent = Collections.unmodifiableSet(extent);
		if (extent.size() == 1)
			iD = extent.iterator().next().getID();
		else iD = nextID++;
	}
	
	protected DenotationSet(Set<IContextObject> extent) {
		this.extent = Collections.unmodifiableSet(extent);
		if (extent.size() == 1)
			iD = extent.iterator().next().getID();
		else iD = nextID++;
	}	

	@Override
	public IDenotationSet buildComplementOfThis(Set<IDenotationSet> complementMinimalLowerBounds) {
		Set<IContextObject> complementExtent = new HashSet<>();
		for (IDenotationSet rebutterMinLowerBound : complementMinimalLowerBounds)
			complementExtent.addAll(rebutterMinLowerBound.getExtent());
		return new ComplementaryDenotationSet(this, complementExtent);
	}

	@Override
	public IDenotationSet complementThisWith(IDenotationSet complementing) {
		return new ComplementaryDenotationSet(this, complementing);
	}
	
	@Override
	public IDenotationSet getComplemented() {
		return null;
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
		return denotations;
	}

	@Override
	public boolean isComplementary() {
		return false;
	}

	@Override
	public int rank() {
		return rank;
	}	
	
	@Override
	public void setRank(int maxPathLengthFromMin) {
		rank = maxPathLengthFromMin;
	}

	@Override
	public String toString() {
		if (type == IDenotationSet.ABSURDITY)
			return "ABSURDITY";
		StringBuilder sB = new StringBuilder();
		sB.append(Integer.toString(iD));
		String newLine = System.lineSeparator();
		sB.append(newLine);
		Iterator<IDenotation> iterator = denotations.iterator();
		while (iterator.hasNext()) {
			sB.append(iterator.next().toString());
			if (iterator.hasNext())
				sB.append(newLine);
		}
		
		return sB.toString();
	}	

}
