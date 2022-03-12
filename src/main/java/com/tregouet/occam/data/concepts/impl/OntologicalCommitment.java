package com.tregouet.occam.data.concepts.impl;

import java.util.Set;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.transitions.IConceptTransition;
import com.tregouet.occam.data.partitions.IPartition;
import com.tregouet.occam.data.preconcepts.IContextObject;
import com.tregouet.occam.data.preconcepts.impl.TruismPreconcept;

public class OntologicalCommitment extends Concept implements IConcept {

	public OntologicalCommitment(Set<IContextObject> extent) {
		super(new TruismPreconcept(extent));
	}

	@Override
	public void loadTransitionRules(IConceptTransition transitions) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IPartition getConceptPartition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IPartition> getSubPartitions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLowestSubordinateID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isUniversal() {
		// TODO Auto-generated method stub
		return false;
	}

}
