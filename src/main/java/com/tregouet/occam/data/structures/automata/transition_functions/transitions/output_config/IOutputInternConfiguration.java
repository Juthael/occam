package com.tregouet.occam.data.structures.automata.transition_functions.transitions.output_config;

public interface IOutputInternConfiguration {

	@Override
	boolean equals(Object o);

	int getOutputStateID();

	@Override
	int hashCode();

}
