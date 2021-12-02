package com.tregouet.occam.data.categories.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.IContextObject;

public class Category extends AbstractCategory implements ICategory {

	private final Set<IIntentAttribute> intent = new HashSet<>();
	private final Set<IContextObject> extent;
	protected int rank = 0;
	private final int iD;
	
	public Category(Set<IConstruct> intent, Set<IContextObject> extent) {
		for (IConstruct construct : intent)
			this.intent.add(new IntentAttribute(construct, this));
		this.extent = Collections.unmodifiableSet(extent);
		if (extent.size() == 1)
			iD = extent.iterator().next().getID();
		else iD = nextID++;
	}
	
	protected Category(Set<IContextObject> extent) {
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
		if (type == ICategory.ABSURDITY)
			return "ABSURDITY";
		StringBuilder sB = new StringBuilder();
		sB.append(Integer.toString(iD));
		//HERE REMOVE /*
		/*
		String newLine = System.lineSeparator();
		sB.append(newLine);
		Iterator<IIntentAttribute> iterator = intent.iterator();
		while (iterator.hasNext()) {
			sB.append(iterator.next().toString());
			if (iterator.hasNext())
				sB.append(newLine);
		}
		*/
		return sB.toString();
	}

	@Override
	public boolean isComplementary() {
		return false;
	}

	@Override
	public ICategory complementThisWith(ICategory complementing) {
		return new ComplementaryCategory(this, complementing);
	}

	@Override
	public ICategory buildComplementOfThis(Set<ICategory> complementMinimalLowerBounds) {
		Set<IContextObject> complementingExtent = new HashSet<>();
		for (ICategory rebutterMinLowerBound : complementMinimalLowerBounds)
			complementingExtent.addAll(rebutterMinLowerBound.getExtent());
		return new ComplementaryCategory(this, complementingExtent);
	}

	@Override
	public ICategory getComplemented() {
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
