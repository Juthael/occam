package com.tregouet.occam.data.automata.transitions.impl;

import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.transitions.IReframerRule;
import com.tregouet.occam.data.automata.transitions.input_config.impl.PushdownAutomatonIC;
import com.tregouet.occam.data.automata.transitions.output_config.impl.PushdownAutomatonOIC;

public class ReframerRule extends PushdownAutomatonTransition implements IReframerRule {

	private final Integer complementedStateID;
	
	public ReframerRule(IState predecessorState, IState complementaryState, int complementedStateID) {
		super(new PushdownAutomatonIC(predecessorState, null, null), new PushdownAutomatonOIC(complementaryState, null));
		this.complementedStateID = complementedStateID;
	}
	
	public ReframerRule(IState complementaryState, IState connectedState) {
		super(new PushdownAutomatonIC(complementaryState, null, null), new PushdownAutomatonOIC(connectedState, null));
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
