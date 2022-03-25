package com.tregouet.occam.data.representations.concepts.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.partitions.IPartition;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.properties.IDifferentiae;
import com.tregouet.occam.data.representations.properties.transitions.IApplication;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;

public class Universal extends Concept implements IConcept {

	private List<IConcept> species;
	private IDifferentiae[] differentiae;
	
	public Universal(IPreconcept preconcept) {
		super(preconcept);
		// TODO Auto-generated constructor stub
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
