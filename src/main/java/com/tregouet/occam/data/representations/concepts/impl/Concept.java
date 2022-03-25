package com.tregouet.occam.data.representations.concepts.impl;

import java.util.Set;

import com.tregouet.occam.data.partitions.IPartition;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.representations.concepts.ConceptType;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.properties.transitions.IApplication;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;

public abstract class Concept implements IConcept {
	
	protected final IPreconcept preconcept;
	protected Integer rank = null;
	
	public Concept(IPreconcept preconcept) {
		this.preconcept = preconcept;
	}

	@Override
	abstract public Set<IPartition> getAllPartitions();
	
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

	@Override
	abstract public void loadTransitionRule(IConceptTransition transition);
	
	@Override
	abstract public void loadTransitionRules(Set<IConceptTransition> transitions);

	@Override
	public int getRank() {
		if (rank != null)
			return rank;
		return 0;
	}

	@Override
	public void setRank(int rank) {
		this.rank = rank;
	}

}
