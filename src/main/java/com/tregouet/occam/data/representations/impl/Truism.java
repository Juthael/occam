package com.tregouet.occam.data.representations.impl;

import com.tregouet.occam.data.automata.tapes.ITapeSet;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.representations.IConcept;

public class Truism extends Universal implements IConcept {

	public Truism(IPreconcept preconcept) {
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

}
