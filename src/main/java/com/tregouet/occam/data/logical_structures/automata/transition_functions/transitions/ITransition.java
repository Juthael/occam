package com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions;

import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.input_config.IInputConfiguration;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.output_config.IOutputInternConfiguration;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;

public interface ITransition<InputSymbol extends ISymbol, InputConfig extends IInputConfiguration<InputSymbol>, OutputConfig extends IOutputInternConfiguration> {

	@Override
	public int hashCode();

	@Override
	boolean equals(Object o);

	InputConfig getInputConfiguration();

	OutputConfig getOutputInternConfiguration();

}
