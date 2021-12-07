package com.tregouet.occam.data.operators.impl;

import com.tregouet.occam.data.operators.IReframer;
import com.tregouet.occam.transition_function.IState;

public class Reframer extends Transition implements IReframer {

	private final String reframing;
	private final boolean blankReframer;
	
	public Reframer(IState complementaryState, IState complementedState, IState successorState) {
		super(complementaryState, successorState);
		reframing = "¬" + Integer.toString(complementedState.getStateID());
		blankReframer = false;
	}
	
	public Reframer(IState predecessorState, IState successorState, String reframing) {
		super(predecessorState, successorState);
		this.reframing = reframing;
		blankReframer = true;
	}
	
	@Override
	public String reframing() {
		return reframing;
	}

	@Override
	public boolean isBlank() {
		return blankReframer;
	}

}
