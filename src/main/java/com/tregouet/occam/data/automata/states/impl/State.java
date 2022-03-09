package com.tregouet.occam.data.automata.states.impl;

import java.util.List;

import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.tapes.ITapeSet;
import com.tregouet.occam.data.preconcepts.IPreconcept;

public class State implements IState {

	private final IPreconcept preconcept;
	@SuppressWarnings("unused")
	//used by unimplemented methods
	private List<ITapeSet> evaluationQueue = null;
	private int rank = 0;
	
	
	public State(IPreconcept preconcept) {
		this.preconcept = preconcept;
	}

	@Override
	public boolean acceptInput(ITapeSet tapeSet) {
		//NOT IMPLEMENTED YET
		return false;
	}

	@Override
	public void generateOutputLanguage() {
		//NOT IMPLEMENTED YET
	}

	@Override
	public IPreconcept getAssociatedPreconcept() {
		return preconcept;
	}

	@Override
	public int getRank() {
		return rank;
	}

	@Override
	public int iD() {
		return preconcept.getID();
	}

	@Override
	public int getStateType() {
		return preconcept.type();
	}

	@Override
	public boolean isActive() {
		//NOT IMPLEMENTED YET
		return false;
	}

	@Override
	public boolean isOperative() {
		//NOT IMPLEMENTED YET
		return false;
	}

	@Override
	public void mergeTapeSets() {
		//NOT IMPLEMENTED YET
	}

	@Override
	public void proceedTransitions() {
		//NOT IMPLEMENTED YET
	}

	@Override
	public void setRank(int rank) {
		//NOT IMPLEMENTED YET
	}

}
