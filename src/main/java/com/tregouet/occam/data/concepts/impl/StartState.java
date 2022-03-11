package com.tregouet.occam.data.concepts.impl;

import com.tregouet.occam.data.automata.states.IState;

public class StartState extends Concept implements IState {

	public static final StartState INSTANCE = new StartState();
	
	private StartState() {
		super(null);
	}

}
