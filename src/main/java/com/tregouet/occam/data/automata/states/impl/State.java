package com.tregouet.occam.data.automata.states.impl;

import java.util.List;

import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.tapes.ITapeSet;
import com.tregouet.occam.data.automata.transitions.ITransition;
import com.tregouet.occam.data.preconcepts.IPreconcept;

public class State implements IState {

	private final IPreconcept preconcept;
	@SuppressWarnings("unused")
	//used by unimplemented methods
	private List<ITransition> transitions = null;
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
	public void loadTransitionRule(ITransition transition) {
		//NOT IMPLEMENTED YET
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (preconcept == null) {
			if (other.preconcept != null)
				return false;
		} else if (!preconcept.equals(other.preconcept))
			return false;
		return true;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((preconcept == null) ? 0 : preconcept.hashCode());
		return result;
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
