package com.tregouet.occam.data.categories.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.constructs.impl.Construct;

public class Category implements ICategory {

	private final Set<IIntentAttribute> intent = new HashSet<>();
	private final Set<IContextObject> extent;
	private int rank = 0;
	private int type;
	
	public Category(Set<IConstruct> intent, Set<IContextObject> extent) {
		for (IConstruct construct : intent)
			this.intent.add(new IntentAttribute(construct, this));
		this.extent = extent;
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
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public Set<IContextObject> getExtent() {
		return extent;
	}

	@Override
	public Set<IIntentAttribute> getIntent() {
		return intent;
	}

	/* 
	 * rank must not be used in hashCode() because of late ranking
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((extent == null) ? 0 : extent.hashCode());
		result = prime * result + ((intent == null) ? 0 : intent.hashCode());
		result = prime * result + type;
		return result;
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
		StringBuilder sB = new StringBuilder();
		String newLine = System.lineSeparator();
		Iterator<IIntentAttribute> iterator = intent.iterator();
		while (iterator.hasNext()) {
			sB.append(iterator.next().toString());
			if (iterator.hasNext())
				sB.append(newLine);
		}
		return sB.toString();
	}
	
	@Override
	public int type() {
		return type;
	}

	@Override
	public boolean meets(List<String> constraintAsStrings) {
		IConstruct constraint = new Construct(constraintAsStrings.toArray(new String[constraintAsStrings.size()]));
		return intent.stream().anyMatch(a -> a.meets(constraint));
	}

	/**
	 * If many attributes meet the constraint, returns the first found. 
	 */
	@Override
	public IIntentAttribute getMatchingAttribute(List<String> constraintAsStrings) {
		IIntentAttribute matchingAttribute = null;
		IConstruct constraintAsConstruct = 
				new Construct(constraintAsStrings.toArray(new String[constraintAsStrings.size()]));
		Iterator<IIntentAttribute> attributeIte = intent.iterator();
		while (matchingAttribute == null && attributeIte.hasNext()) {
			IIntentAttribute currAtt = attributeIte.next();
			if (currAtt.meets(constraintAsConstruct))
				matchingAttribute = currAtt;
		}
		return matchingAttribute;
	}	

}
