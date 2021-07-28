package com.tregouet.occam.data.categories.impl;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.IContextObject;

public class Category implements ICategory {

	public static final int LATT_MIN = 0;
	public static final int LATT_OBJ = 1;
	public static final int LATT_CAT = 2;
	public static final int LATT_MAX = 3;
	public static final int PREACCEPT = 4;
	public static final int ACCEPT = 5;
	
	private final Set<IIntentAttribute> intent = new HashSet<>();
	private final Set<IContextObject> extent;
	private int rank = 0;
	private int type;
	
	public Category(Set<IConstruct> intent, Set<IContextObject> extent) {
		for (IConstruct construct : intent)
			intent.add(new IntentAttribute(construct, this));
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
	 * rank must not be used in hashcode() because of late ranking
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
	public int type() {
		return type;
	}

}
