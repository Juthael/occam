package com.tregouet.occam.data.automata.transitions.output_config;

import com.tregouet.occam.data.automata.states.IState;

public interface IOutputInternConfiguration {
	
	IState getOutputState();
	
	@Override
	boolean equals(Object o);
	
	@Override
	int hashCode();

}
