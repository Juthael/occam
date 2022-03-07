package com.tregouet.occam.data.denotations.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.tregouet.occam.data.denotations.IPreconcept;
import com.tregouet.occam.data.denotations.IContextObject;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.languages.generic.IConstruct;

public class Preconcept extends AbstractPreconcept implements IPreconcept {

	private final Set<IDenotation> denotations = new HashSet<>();
	private final Set<IContextObject> extent;
	protected int rank = 0;
	private final int iD;
	
	public Preconcept(Set<IConstruct> denotatingConstructs, Set<IContextObject> extent) {
		for (IConstruct construct : denotatingConstructs)
			this.denotations.add(new Denotation(construct, this));
		this.extent = Collections.unmodifiableSet(extent);
		if (extent.size() == 1)
			iD = extent.iterator().next().getID();
		else iD = nextID++;
	}
	
	protected Preconcept(Set<IContextObject> extent) {
		this.extent = Collections.unmodifiableSet(extent);
		if (extent.size() == 1)
			iD = extent.iterator().next().getID();
		else iD = nextID++;
	}	

	@Override
	public IPreconcept buildComplementOfThis(Set<IPreconcept> complementMinimalLowerBounds) {
		Set<IContextObject> complementExtent = new HashSet<>();
		for (IPreconcept rebutterMinLowerBound : complementMinimalLowerBounds)
			complementExtent.addAll(rebutterMinLowerBound.getExtent());
		return new ComplementaryPreconcept(this, complementExtent);
	}

	@Override
	public IPreconcept complementThisWith(IPreconcept complementing) {
		return new ComplementaryPreconcept(this, complementing);
	}
	
	@Override
	public IPreconcept getComplemented() {
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
		if (type == IPreconcept.ABSURDITY)
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
