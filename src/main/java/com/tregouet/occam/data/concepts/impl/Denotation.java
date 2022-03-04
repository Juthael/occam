package com.tregouet.occam.data.concepts.impl;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IDenotation;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;

public class Denotation extends Construct implements IDenotation {

	private final IConcept concept;
	
	public Denotation(IConstruct construct, IConcept concept) {
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
		Denotation other = (Denotation) obj;
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
		//must not use concept hashCode(), since concept hashCode() uses this'. 
		return super.hashCode();
	}

}
