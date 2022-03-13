package com.tregouet.occam.data.concepts.impl;

import java.util.Set;

import com.tregouet.occam.data.concepts.ConceptualType;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.transitions.IConceptTransition;
import com.tregouet.occam.data.partitions.IPartition;
import com.tregouet.occam.data.preconcepts.IPreconcept;

public abstract class Concept implements IConcept {
	
	IPreconcept preconcept;
	
	public Concept(IPreconcept preconcept) {
		super();
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
	abstract public void loadTransitionRules(IConceptTransition transitions);

	@Override
	abstract public IPartition getConceptPartition();

	@Override
	abstract public Set<IPartition> getSubPartitions();

	@Override
	abstract public int getLowestSubordinateID();

	@Override
	abstract public boolean isUniversal();

	@Override
	public ConceptualType type() {
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
