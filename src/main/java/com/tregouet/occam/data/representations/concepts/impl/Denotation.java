package com.tregouet.occam.data.representations.concepts.impl;

import com.tregouet.occam.data.languages.words.construct.IConstruct;
import com.tregouet.occam.data.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IDenotation;

public class Denotation extends Construct implements IDenotation {

	private final IConcept concept;
	private boolean isRedundant = false;
	
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

	@Override
	public Integer compareTo(IDenotation other) {
		return DenotationComparator.INSTANCE.compare(this, other);
	}

	@Override
	public boolean isRedundant() {
		return isRedundant;
	}

	@Override
	public void markAsRedundant() {
		isRedundant = true;
	}

}
