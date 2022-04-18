package com.tregouet.occam.data.logical_structures.automata.transition_functions;

import java.util.Set;

import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.ITransition;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.input_config.IInputConfiguration;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.output_config.IOutputInternConfiguration;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;

public interface ITransitionFunction<InputSymbol extends ISymbol, InputConfig extends IInputConfiguration<InputSymbol>, OutputConfig extends IOutputInternConfiguration, Transition extends ITransition<InputSymbol, InputConfig, OutputConfig>> {

	@Override
	boolean equals(Object other);

	Set<Integer> getAcceptStateIDs();

	Set<InputSymbol> getInputAlphabet();

	int getStartStateID();

	Set<Integer> getStateIDs();

	Set<Transition> getTransitions();

	@Override
	int hashCode();

}
