package com.tregouet.occam.data.abstract_machines.states.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.tapes.ITapeSet;
import com.tregouet.occam.data.abstract_machines.transitions.IOperator;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IDenotation;

public class State implements IState {

	private final IDenotationSet denotationSet;
	private List<IOperator> transitions = null;
	@SuppressWarnings("unused")
	//used by unimplemented methods
	private List<ITapeSet> evaluationQueue = null;
	private int rank = 0;
	
	
	public State(IDenotationSet denotationSet) {
		this.denotationSet = denotationSet;
	}

	@Override
	public boolean acceptInput(ITapeSet tapeSet) {
		//Not implemented yet
		/*
		if (!isActive())
			return false;
		for (IOperator operator : transitions) {
			if (operator.operateOn(tapeSet.))
				
		}
		*/
		return false;
	}

	@Override
	public void addTransition(IOperator operator) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
		// TODO Auto-generated method stub

	}

	@Override
	public IDenotationSet getAssociatedDenotationSet() {
		return denotationSet;
	}

	@Override
	public Set<IDenotation> getInputLanguage() {
		if (this.isActive()) {
			Set<IDenotation> inputlanguage = new HashSet<>();
			for (IOperator operator : transitions) {
				for (IProduction prod : operator.operation())
					inputlanguage.add(prod.getSource());
			}
			return inputlanguage;
		}
		else {
			//not proper input language. For tests use. 
			return denotationSet.getDenotations();
		}
	}

	@Override
	public int getRank() {
		return rank;
	}

	@Override
	public int getStateID() {
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOperative() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void mergeTapeSetsWithSameInput() {
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
