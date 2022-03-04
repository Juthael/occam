package com.tregouet.occam.data.concepts.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IContextObject;
import com.tregouet.occam.data.concepts.IDenotation;
import com.tregouet.occam.data.languages.generic.IConstruct;

public class Concept extends AbstractConcept implements IConcept {

	private final Set<IDenotation> denotations = new HashSet<>();
	private final Set<IContextObject> extent;
	protected int rank = 0;
	private final int iD;
	
	public Concept(Set<IConstruct> denotatingConstructs, Set<IContextObject> extent) {
		for (IConstruct construct : denotatingConstructs)
			this.denotations.add(new Denotation(construct, this));
		this.extent = Collections.unmodifiableSet(extent);
		if (extent.size() == 1)
			iD = extent.iterator().next().getID();
		else iD = nextID++;
	}
	
	protected Concept(Set<IContextObject> extent) {
		this.extent = Collections.unmodifiableSet(extent);
		if (extent.size() == 1)
			iD = extent.iterator().next().getID();
		else iD = nextID++;
	}	

	@Override
	public IConcept buildComplementOfThis(Set<IConcept> complementMinimalLowerBounds) {
		Set<IContextObject> complementExtent = new HashSet<>();
		for (IConcept rebutterMinLowerBound : complementMinimalLowerBounds)
			complementExtent.addAll(rebutterMinLowerBound.getExtent());
		return new ComplementaryConcept(this, complementExtent);
	}

	@Override
	public IConcept complementThisWith(IConcept complementing) {
		return new ComplementaryConcept(this, complementing);
	}
	
	@Override
	public IConcept getComplemented() {
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
		if (type == IConcept.ABSURDITY)
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
