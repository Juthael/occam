package com.tregouet.occam.data.categories.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.constructs.impl.Construct;
import com.tregouet.occam.exceptions.PropertyTargetingException;

public abstract class AbstractCategory implements ICategory {

	protected static int nextID = 100;
	
	protected int type;
	
	public AbstractCategory() {
		// TODO Auto-generated constructor stub
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
		Iterator<IIntentAttribute> attributeIte = getIntent().iterator();
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

	@Override
	public boolean meets(IConstruct constraint) {
		return getIntent().stream().anyMatch(a -> a.meets(constraint));
	}

	@Override
	public boolean meets(List<String> constraintAsStrings) {
		IConstruct constraint = new Construct(constraintAsStrings.toArray(new String[constraintAsStrings.size()]));
		return meets(constraint);
	}

	@Override
	public void setType(int type) {
		this.type = type;
	}

	@Override
	public int type() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getID();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AbstractCategory))
			return false;
		AbstractCategory other = (AbstractCategory) obj;
		if (getID() != other.getID())
			return false;
		return true;
	}

}
