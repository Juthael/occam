package com.tregouet.occam.data.denotations.impl;

import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;

public class Denotation extends Construct implements IDenotation {

	private final IDenotationSet denotationSet;
	
	public Denotation(IConstruct construct, IDenotationSet denotationSet) {
		super(construct);
		this.denotationSet = denotationSet;
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
		if (denotationSet == null) {
			if (other.denotationSet != null)
				return false;
		} else if (!denotationSet.equals(other.denotationSet))
			return false;
		return true;
	}
	
	@Override
	public IDenotationSet getDenotationSet() {
		return denotationSet;
	}

	@Override
	public int hashCode() {
		//must not use Category.hashCode(), since Category.hashCode() uses this'. 
		return super.hashCode();
	}

}
