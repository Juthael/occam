package com.tregouet.occam.data.representations.concepts.impl;

import java.util.Set;

import com.tregouet.occam.data.partitions.IPartition;
import com.tregouet.occam.data.preconcepts.IContextObject;
import com.tregouet.occam.data.preconcepts.impl.ThisPreconcept;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.properties.transitions.IApplication;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;

public class OntologicalCommitment extends Concept implements IConcept {

	public OntologicalCommitment(Set<IContextObject> extent) {
		super(new ThisPreconcept(extent));
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<IPartition> getAllPartitions() {
		// TODO Auto-generated method stub
		return null;
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
	public void loadTransitionRule(IConceptTransition transition) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadTransitionRules(Set<IConceptTransition> transitions) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<IApplication> getApplications() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IConceptTransition> getConceptTransitions() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
