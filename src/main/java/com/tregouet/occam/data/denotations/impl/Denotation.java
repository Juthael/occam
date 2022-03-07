package com.tregouet.occam.data.denotations.impl;

import com.tregouet.occam.data.denotations.IPreconcept;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;

public class Denotation extends Construct implements IDenotation {

	private final IPreconcept preconcept;
	
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
		//must not use concept hashCode(), since concept hashCode() uses this'. 
		return super.hashCode();
	}

}
