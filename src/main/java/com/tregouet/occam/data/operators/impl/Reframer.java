package com.tregouet.occam.data.operators.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.operators.IReframer;
import com.tregouet.occam.transition_function.IState;

public class Reframer extends Transition implements IReframer {

	private final List<Integer> complementedStateIDs = new ArrayList<>();
	private final boolean blankReframer;
	
	public Reframer(IState complementaryState, IState complementedState, IState successorState) {
		super(complementaryState, successorState);
		complementedStateIDs.add(complementaryState.getStateID());
		blankReframer = false;
	}
	
	public Reframer(IState predecessorState, IState successorState, String reframing) {
		super(predecessorState, successorState);
		blankReframer = true;
	}
	
	@Override
	public String getReframer() {
		if (!blankReframer) {
			return "¬" + Integer.toString(complementedStateIDs.get(complementedStateIDs.size() - 1));
		}
		return new String();		
	}

	@Override
	public boolean isBlank() {
		return blankReframer;
	}

	@Override
	public void reframe(IState complementedState) {
		complementedStateIDs.add(complementedState.getStateID());
	}

}
