package com.tregouet.occam.data.operators.impl;

import com.tregouet.occam.data.operators.IReframer;
import com.tregouet.occam.transition_function.IState;

public class Reframer extends Transition implements IReframer {

	private final String reframing;
	
	public Reframer(IState complementaryState, IState complementedState, IState successorState) {
		super(complementaryState, successorState);
		reframing = "¬" + Integer.toString(complementedState.getStateID());
	}
	@Override
	public String reframing() {
		return reframing;
	}

}
