package com.tregouet.occam.data.abstract_machines.transitions.impl;

import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transitions.IReframer;

public class Reframer extends Transition implements IReframer {

	private final Integer complementedStateID;
	private Double cost = null;
	
	public Reframer(IState complementaryState, int complementedStateID, IState successorState) {
		super(complementaryState, successorState);
		this.complementedStateID = complementedStateID;
	}
	
	public Reframer(IState connectedState, IState complementaryState) {
		super(connectedState, complementaryState);
		complementedStateID = null;
	}	
	
	@Override
	public Integer getComplementedStateID() {
		return complementedStateID;
	}
	
	@Override
	public Double getCost() {
		return cost;
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
	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		if (isBlank())
			return new String();
		return "¬" + Integer.toString(complementedStateID);
	}	

}
