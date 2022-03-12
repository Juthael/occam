package com.tregouet.occam.data.automata.states.impl;

import java.util.List;

import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.tapes.ITapeSet;

public abstract class State implements IState {

	@SuppressWarnings("unused")
	//used by unimplemented methods
	private List<ITapeSet> evaluationQueue = null;
	private int rank = 0;
	
	
	public State() {
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
	public int getRank() {
		return rank;
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

	@Override
	abstract public int iD();

}
