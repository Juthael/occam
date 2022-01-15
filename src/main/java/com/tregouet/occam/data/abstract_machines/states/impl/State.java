package com.tregouet.occam.data.abstract_machines.states.impl;

import java.util.List;

import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.tapes.ITapeSet;
import com.tregouet.occam.data.abstract_machines.transition_rules.ITransitionRule;
import com.tregouet.occam.data.denotations.IDenotationSet;

public class State implements IState {

	private final IDenotationSet denotationSet;
	@SuppressWarnings("unused")
	//used by unimplemented methods
	private List<ITransitionRule> transitionRules = null;
	@SuppressWarnings("unused")
	//used by unimplemented methods
	private List<ITapeSet> evaluationQueue = null;
	private int rank = 0;
	
	
	public State(IDenotationSet denotationSet) {
		this.denotationSet = denotationSet;
	}

	@Override
	public boolean acceptInput(ITapeSet tapeSet) {
		//NOT IMPLEMENTED YET
		return false;
	}

	@Override
	public void loadTransitionRule(ITransitionRule transitionRule) {
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
		if (denotationSet == null) {
			if (other.denotationSet != null)
				return false;
		} else if (!denotationSet.equals(other.denotationSet))
			return false;
		return true;
	}

	@Override
	public void generateOutputLanguage() {
		//NOT IMPLEMENTED YET
	}

	@Override
	public IDenotationSet getAssociatedDenotationSet() {
		return denotationSet;
	}

	@Override
	public int getRank() {
		return rank;
	}

	@Override
	public int iD() {
		return denotationSet.getID();
	}

	@Override
	public int getStateType() {
		return denotationSet.type();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((denotationSet == null) ? 0 : denotationSet.hashCode());
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
	public void mergeTapeSetsWithSameInput() {
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
