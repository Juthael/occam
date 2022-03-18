package com.tregouet.occam.data.automata.transitions.output_config;

public interface IOutputInternConfiguration {
	
	int getOutputStateID();
	
	@Override
	boolean equals(Object o);
	
	@Override
	int hashCode();

}
