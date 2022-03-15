package com.tregouet.occam.data.representations.impl;

import java.util.Set;

import com.tregouet.occam.data.automata.tapes.ITapeSet;
import com.tregouet.occam.data.partitions.IPartition;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.representations.IConcept;
import com.tregouet.occam.data.representations.transitions.IApplication;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;

public class Particular extends Concept implements IConcept {

	public Particular(IPreconcept preconcept) {
		super(preconcept);
	}

	@Override
	public boolean isOperative() {
		return true;
	}

	@Override
	public void proceedTransitions() {
		// No more transitions from a particular
	}

	@Override
	public void setRank(int rank) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadTransitionRule(IConceptTransition transitions) {
		//No transition from a particular
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
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSubconceptsDistinctiveFeaturesSalience() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSpeciesAlignableDifferencesSalience() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSaliences() {
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

	@Override
	public Set<IPartition> getAllPartitions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadTransitionRules(Set<IConceptTransition> transitions) {
		// TODO Auto-generated method stub
		
	}

}
