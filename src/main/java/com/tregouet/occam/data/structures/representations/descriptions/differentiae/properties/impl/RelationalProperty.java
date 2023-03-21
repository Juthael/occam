package com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.impl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.tregouet.occam.data.structures.representations.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IRelationalProperty;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.IComputation;

public class RelationalProperty extends Property implements IRelationalProperty {
	
	private final Set<IDenotation> functionSet;

	public RelationalProperty(int genusID, int speciesID, Set<IDenotation> functionSet, Set<IComputation> computations,
			int nbOfSignificantComp) {
		super(genusID, speciesID, null, computations, nbOfSignificantComp);
		this.functionSet = functionSet;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(computations, functionSet, speciesID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelationalProperty other = (RelationalProperty) obj;
		return Objects.equals(functionSet, other.functionSet);
	}
	
	@Override
	public boolean isRelational() {
		return true;
	}

	@Override
	public Set<IDenotation> getFunctionSet() {
		return new HashSet<>(functionSet);
	}

}
