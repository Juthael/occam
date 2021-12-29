package com.tregouet.occam.data.abstract_machines.transitions.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transitions.IReframer;

public class Reframer extends Transition implements IReframer {

	private final List<Integer> complementedStateIDs = new ArrayList<>();
	private final boolean blankReframer;
	private Double cost = null;
	
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
	public List<Integer> getComplementedConceptsIDs() {
		return new ArrayList<>(complementedStateIDs);
	}

	@Override
	public Double getCost() {
		return cost;
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
	public boolean isReframer() {
		return true;
	}
	
	@Override
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	@Override
	public String toString() {
		return getReframer();
	}	

}
