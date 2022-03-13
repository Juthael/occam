package com.tregouet.occam.data.concepts.impl;

import java.util.Set;

import com.tregouet.occam.data.automata.tapes.ITapeSet;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.transitions.IConceptTransition;
import com.tregouet.occam.data.partitions.IPartition;
import com.tregouet.occam.data.preconcepts.IPreconcept;

public class Particular extends Concept implements IConcept {

	public Particular(IPreconcept preconcept) {
		super(preconcept);
	}

	@Override
	public boolean evaluate(ITapeSet tapeSet) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void generateOutputLanguage() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getRank() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOperative() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void mergeTapeSets() {
		// TODO Auto-generated method stub

	}

	@Override
	public void proceedTransitions() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRank(int rank) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadTransitionRules(IConceptTransition transitions) {
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

}
