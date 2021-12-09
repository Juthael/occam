package com.tregouet.occam.data.concepts.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;

public abstract class AbstractConcept implements IConcept {

	protected static int nextID = 100;
	
	protected int type;
	
	public AbstractConcept() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AbstractConcept))
			return false;
		AbstractConcept other = (AbstractConcept) obj;
		if (getID() != other.getID())
			return false;
		return true;
	}	

	/**
	 * If many attributes meet the constraint, returns the first found. 
	 * @throws PropertyTargetingException 
	 */
	@Override
	public IIntentAttribute getMatchingAttribute(List<String> constraintAsStrings) throws IOException {
		IIntentAttribute matchingAttribute = null;
		IConstruct constraintAsConstruct = 
				new Construct(constraintAsStrings.toArray(new String[constraintAsStrings.size()]));
		Iterator<IIntentAttribute> attributeIte = getIntent().iterator();
		while (attributeIte.hasNext()) {
			IIntentAttribute currAtt = attributeIte.next();
			if (currAtt.meets(constraintAsConstruct)) {
				if (matchingAttribute == null)
					matchingAttribute = currAtt;
				else throw new IOException("Category.getMatchingAttribute(List<String>) : "
						+ "the constraint is not specific enough to target a single attribute.");
			}
		}
		return matchingAttribute;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getID();
		return result;
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

}
