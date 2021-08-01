package com.tregouet.occam.data.operators.impl;

import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.constructs.AVariable;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.operators.IProduction;

public class BlankProduction extends Production implements IProduction {

	private final int iD;
	private static int nextID = 0;
	
	public BlankProduction(AVariable variable, IConstruct value, IIntentAttribute operatorInput,
			IIntentAttribute operatorOutput) {
		super(value, operatorInput, operatorOutput);
		iD = nextID++;
	}

	@Override
	public IConstruct derive(IConstruct construct) {
		return construct;
	}

	@Override
	public boolean derives(AVariable var) {
		return false;
	}

	@Override
	public IConstruct doAbstract(IConstruct construct) {
		return construct;
	}
	
	@Override
	public String toString() {
		return new String();  
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + iD;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlankProduction other = (BlankProduction) obj;
		if (iD != other.iD)
			return false;
		return true;
	}

}
