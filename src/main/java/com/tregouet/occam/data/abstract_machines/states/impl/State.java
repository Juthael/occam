package com.tregouet.occam.data.abstract_machines.states.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.tapes.ITapeSet;
import com.tregouet.occam.data.abstract_machines.transitions.IOperator;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIntentAttribute;

public class State implements IState {

	private final IConcept concept;
	private List<IOperator> transitions = null;
	private List<ITapeSet> evaluationQueue = new ArrayList<>();
	private int rank = 0;
	
	
	public State(IConcept concept) {
		this.concept = concept;
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
		if (concept == null) {
			if (other.concept != null)
				return false;
		} else if (!concept.equals(other.concept))
			return false;
		return true;
	}

	@Override
	public void generateOutputLanguage() {
		// TODO Auto-generated method stub

	}

	@Override
	public IConcept getAssociatedConcept() {
		return concept;
	}

	@Override
	public Set<IIntentAttribute> getInputLanguage() {
		if (this.isActive()) {
			Set<IIntentAttribute> inputlanguage = new HashSet<>();
			for (IOperator operator : transitions) {
				for (IProduction prod : operator.operation())
					inputlanguage.add(prod.getSource());
			}
			return inputlanguage;
		}
		else {
			//not proper input language. For tests use. 
			return concept.getIntent();
		}
	}

	@Override
	public int getRank() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getStateID() {
		return concept.getID();
	}

	@Override
	public int getStateType() {
		return concept.type();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((concept == null) ? 0 : concept.hashCode());
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
