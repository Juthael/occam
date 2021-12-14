package com.tregouet.occam.data.concepts.impl;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIntentConstruct;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;

public class IntentConstruct extends Construct implements IIntentConstruct {

	private final IConcept concept;
	
	public IntentConstruct(IConstruct construct, IConcept concept) {
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
		IntentConstruct other = (IntentConstruct) obj;
		if (concept == null) {
			if (other.concept != null)
				return false;
		} else if (!concept.equals(other.concept))
			return false;
		return true;
	}
	
	@Override
	public IConcept getConcept() {
		return concept;
	}

	@Override
	public int hashCode() {
		//must not use Category.hashCode(), since Category.hashCode() uses this'. 
		return super.hashCode();
	}

}
