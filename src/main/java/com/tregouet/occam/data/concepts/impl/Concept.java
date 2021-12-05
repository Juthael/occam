package com.tregouet.occam.data.concepts.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.IContextObject;

public class Concept extends AbstractConcept implements IConcept {

	private final Set<IIntentAttribute> intent = new HashSet<>();
	private final Set<IContextObject> extent;
	protected int rank = 0;
	private final int iD;
	
	public Concept(Set<IConstruct> intent, Set<IContextObject> extent) {
		for (IConstruct construct : intent)
			this.intent.add(new IntentAttribute(construct, this));
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
	public Set<IContextObject> getExtent() {
		return extent;
	}

	@Override
	public Set<IIntentAttribute> getIntent() {
		return intent;
	}
	
	@Override
	public int getID() {
		return iD;
	}	

	@Override
	public String toString() {
		if (type == IConcept.ABSURDITY)
			return "ABSURDITY";
		StringBuilder sB = new StringBuilder();
		sB.append(Integer.toString(iD));
		//HERE REMOVE
		
		String newLine = System.lineSeparator();
		sB.append(newLine);
		Iterator<IIntentAttribute> iterator = intent.iterator();
		while (iterator.hasNext()) {
			sB.append(iterator.next().toString());
			if (iterator.hasNext())
				sB.append(newLine);
		}
		
		return sB.toString();
	}

	@Override
	public boolean isComplementary() {
		return false;
	}

	@Override
	public IConcept complementThisWith(IConcept complementing) {
		return new ComplementaryConcept(this, complementing);
	}

	@Override
	public IConcept buildComplementOfThis(Set<IConcept> complementMinimalLowerBounds) {
		Set<IContextObject> complementExtent = new HashSet<>();
		for (IConcept rebutterMinLowerBound : complementMinimalLowerBounds)
			complementExtent.addAll(rebutterMinLowerBound.getExtent());
		return new ComplementaryConcept(this, complementExtent);
	}

	@Override
	public IConcept getComplemented() {
		return null;
	}	
	
	@Override
	public int rank() {
		return rank;
	}

	@Override
	public void setRank(int maxPathLengthFromMin) {
		rank = maxPathLengthFromMin;
	}	

}
