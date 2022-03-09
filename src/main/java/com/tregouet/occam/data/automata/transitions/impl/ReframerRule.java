package com.tregouet.occam.data.automata.transitions.impl;

import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.transitions.IReframerRule;
import com.tregouet.occam.data.concepts.transitions.ConceptTransition;
import com.tregouet.occam.data.concepts.transitions.ConceptTransitionIC;
import com.tregouet.occam.data.concepts.transitions.ConceptTransitionOIC;

public class ReframerRule extends ConceptTransition implements IReframerRule {

	private final Integer complementedStateID;
	
	public ReframerRule(IState predecessorState, IState complementaryState, int complementedStateID) {
		super(new ConceptTransitionIC(predecessorState, null, null), new ConceptTransitionOIC(complementaryState, null));
		this.complementedStateID = complementedStateID;
	}
	
	public ReframerRule(IState complementaryState, IState connectedState) {
		super(new ConceptTransitionIC(complementaryState, null, null), new ConceptTransitionOIC(connectedState, null));
		complementedStateID = null;
	}	
	
	@Override
	public Integer getComplementedStateID() {
		return complementedStateID;
	}

	@Override
	public boolean isBlank() {
		return (complementedStateID == null);
	}
	
	@Override
	public boolean isConnector() {
		return (complementedStateID == null);
	}
	
	@Override
	public boolean isReframer() {
		return true;
	}

	@Override
	public String toString() {
		if (isBlank())
			return new String();
		return "¬" + Integer.toString(complementedStateID);
	}	

}
