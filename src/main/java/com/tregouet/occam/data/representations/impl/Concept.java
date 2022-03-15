package com.tregouet.occam.data.representations.impl;

import java.util.Set;

import com.tregouet.occam.data.partitions.IPartition;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.representations.ConceptType;
import com.tregouet.occam.data.representations.IConcept;

public abstract class Concept implements IConcept {
	
	IPreconcept preconcept;
	
	public Concept(IPreconcept preconcept) {
		this.preconcept = preconcept;
	}

	@Override
	public Set<IPartition> getAllPartitions() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getID() {
		return preconcept.getID();
	}

	@Override
	abstract public IPartition getConceptPartition();

	@Override
	abstract public Set<IPartition> getSubPartitions();

	@Override
	abstract public int getLowestSubordinateID();

	@Override
	public ConceptType type() {
		return getPreconcept().type();
	}

	@Override
	public IPreconcept getPreconcept() {
		return preconcept;
	}

	@Override
	public int iD() {
		return preconcept.getID();
	}

}
