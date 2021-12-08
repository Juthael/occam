package com.tregouet.occam.data.operators.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.operators.IReframer;
import com.tregouet.occam.transition_function.IState;

public class Reframer extends Transition implements IReframer {

	private final List<Integer> complementedStateIDs = new ArrayList<>();
	private final boolean blankReframer;
	
	public Reframer(IState complementaryState, IState complementedState, IState successorState, 
			List<Integer> previousComplementedStatesID) {
		super(complementaryState, successorState);
		complementedStateIDs.addAll(previousComplementedStatesID);
		complementedStateIDs.add(complementedState.getStateID());
		blankReframer = false;
	}
	
	public Reframer(IState predecessorState, IState successorState, List<Integer> previousComplementedStatesID) {
		super(predecessorState, successorState);
		complementedStateIDs.addAll(previousComplementedStatesID);
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
	public List<Integer> getComplementedConceptsIDs() {
		return new ArrayList<>(complementedStateIDs);
	}

}
