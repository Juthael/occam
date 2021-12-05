package com.tregouet.occam.data.concepts.impl;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.impl.Construct;

public class IntentAttribute extends Construct implements IIntentAttribute {

	private final IConcept concept;
	
	public IntentAttribute(IConstruct construct, IConcept concept) {
		super(construct);
		this.concept = concept;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntentAttribute other = (IntentAttribute) obj;
		if (concept == null) {
			if (other.concept != null)
				return false;
		} else if (!concept.equals(other.concept))
			return false;
		return true;
	}
	
	@Override
	public IConcept getCategory() {
		return concept;
	}

	@Override
	public int hashCode() {
		//must not use Category.hashCode(), since Category.hashCode() uses this'. 
		return super.hashCode();
	}

}
