package com.tregouet.occam.data.automata.transitions.impl;

import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.transitions.IReframerRule;
import com.tregouet.occam.data.concepts.transitions.RepresentationTransition;
import com.tregouet.occam.data.concepts.transitions.RepresentationTransitionIC;
import com.tregouet.occam.data.concepts.transitions.RepresentationTransitionOIC;

public class ReframerRule extends RepresentationTransition implements IReframerRule {

	private final Integer complementedStateID;
	
	public ReframerRule(IState predecessorState, IState complementaryState, int complementedStateID) {
		super(new RepresentationTransitionIC(predecessorState, null, null), new RepresentationTransitionOIC(complementaryState, null));
		this.complementedStateID = complementedStateID;
	}
	
	public ReframerRule(IState complementaryState, IState connectedState) {
		super(new RepresentationTransitionIC(complementaryState, null, null), new RepresentationTransitionOIC(connectedState, null));
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
