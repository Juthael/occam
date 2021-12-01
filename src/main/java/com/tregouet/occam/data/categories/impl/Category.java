package com.tregouet.occam.data.categories.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.constructs.impl.Construct;
import com.tregouet.occam.exceptions.PropertyTargetingException;

public class Category implements ICategory {

	private static int nextID = 100;
	
	private final Set<IIntentAttribute> intent = new HashSet<>();
	private final Set<IContextObject> extent;
	private int rank = 0;
	private int type;
	private final int iD;
	private ICategory rebuttedByThis = null;
	
	public Category(Set<? extends IConstruct> intent, Set<IContextObject> extent) {
		for (IConstruct construct : intent)
			this.intent.add(new IntentAttribute(construct, this));
		this.extent = Collections.unmodifiableSet(extent);
		if (extent.size() == 1)
			iD = extent.iterator().next().getID();
		else iD = nextID++;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (iD != other.iD)
			return false;
		if (extent == null) {
			if (other.extent != null)
				return false;
		} else if (!extent.equals(other.extent))
			return false;
		if (intent == null) {
			if (other.intent != null)
				return false;
		} else if (!intent.equals(other.intent))
			return false;
		return true;
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
		return intent;
	}

	/**
	 * If many attributes meet the constraint, returns the first found. 
	 * @throws PropertyTargetingException 
	 */
	@Override
	public IIntentAttribute getMatchingAttribute(List<String> constraintAsStrings) throws PropertyTargetingException {
		IIntentAttribute matchingAttribute = null;
		IConstruct constraintAsConstruct = 
				new Construct(constraintAsStrings.toArray(new String[constraintAsStrings.size()]));
		Iterator<IIntentAttribute> attributeIte = intent.iterator();
		while (attributeIte.hasNext()) {
			IIntentAttribute currAtt = attributeIte.next();
			if (currAtt.meets(constraintAsConstruct)) {
				if (matchingAttribute == null)
					matchingAttribute = currAtt;
				else throw new PropertyTargetingException("Category.getMatchingAttribute(List<String>) : "
						+ "the constraint is not specific enough to target a single attribute.");
			}
		}
		return matchingAttribute;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((extent == null) ? 0 : extent.hashCode());
		result = prime * result + iD;
		result = prime * result + ((intent == null) ? 0 : intent.hashCode());
		return result;
	}
	
	@Override
	public boolean meets(IConstruct constraint) {
		return intent.stream().anyMatch(a -> a.meets(constraint));
	}

	@Override
	public boolean meets(List<String> constraintAsStrings) {
		IConstruct constraint = new Construct(constraintAsStrings.toArray(new String[constraintAsStrings.size()]));
		return meets(constraint);
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
	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		if (type == ICategory.ABSURDITY)
			return "ABSURDITY";
		if (isRebutter())
			return Integer.toString(-rebuttedByThis.getID());
		StringBuilder sB = new StringBuilder();
		String newLine = System.lineSeparator();
		sB.append(Integer.toString(iD));
		//HERE REMOVE /*
		/*
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
	public int type() {
		return type;
	}

	@Override
	public boolean isRebutter() {
		return (rebuttedByThis != null);
	}

	@Override
	public ICategory rebutThisWith(ICategory rebutting) {
		ICategory rebutter = new Category(rebutting.getIntent(), rebutting.getExtent());
		rebutter.setType(type);
		rebutter.setAsRebutterOf(this);
		return rebutter;
	}

	@Override
	public void setAsRebutterOf(ICategory rebutted) {
		rebuttedByThis = rebutted;		
	}

	@Override
	public ICategory buildRebutterOfThis(Set<ICategory> rebutterMinimalLowerBounds) {
		Set<IContextObject> rebutterExtent = new HashSet<>();
		for (ICategory rebutterMinLowerBound : rebutterMinimalLowerBounds)
			rebutterExtent.addAll(rebutterMinLowerBound.getExtent());
		ICategory rebutter = new Category(new HashSet<IConstruct>(), rebutterExtent);
		rebutter.setAsRebutterOf(this);
		rebutter.setType(ICategory.SUBSET_CAT);
		return rebutter;
	}

	@Override
	public ICategory getRebutted() {
		return rebuttedByThis;
	}	

}
