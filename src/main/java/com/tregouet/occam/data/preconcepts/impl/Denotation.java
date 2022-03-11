package com.tregouet.occam.data.preconcepts.impl;

import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;
import com.tregouet.occam.data.preconcepts.IDenotation;
import com.tregouet.occam.data.preconcepts.IPreconcept;

public class Denotation extends Construct implements IDenotation {

	private final IPreconcept preconcept;
	private boolean isRedundant = false;
	
	public Denotation(IConstruct construct, IPreconcept preconcept) {
		super(construct);
		this.preconcept = preconcept;
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
		if (preconcept == null) {
			if (other.preconcept != null)
				return false;
		} else if (!preconcept.equals(other.preconcept))
			return false;
		return true;
	}
	
	@Override
	public IPreconcept getConcept() {
		return preconcept;
	}

	@Override
	public int hashCode() {
		//must not use preconcept hashCode(), since preconcept hashCode() uses this'. 
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
