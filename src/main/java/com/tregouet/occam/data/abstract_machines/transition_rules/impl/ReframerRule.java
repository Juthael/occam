package com.tregouet.occam.data.abstract_machines.transition_rules.impl;

import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transition_rules.IReframerRule;

public class ReframerRule extends TransitionRule implements IReframerRule {

	private final Integer complementedStateID;
	
	public ReframerRule(IState predecessorState, IState complementaryState, int complementedStateID) {
		super(new InputConfiguration(predecessorState, null, null), new OutputInternConfiguration(complementaryState, null));
		this.complementedStateID = complementedStateID;
	}
	
	public ReframerRule(IState complementaryState, IState connectedState) {
		super(new InputConfiguration(complementaryState, null, null), new OutputInternConfiguration(connectedState, null));
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
