package com.tregouet.occam.data.automata.transition_functions;

import java.util.Collection;
import java.util.Set;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.transition_functions.transitions.ITransition;
import com.tregouet.occam.data.automata.transition_functions.transitions.input_config.IInputConfiguration;
import com.tregouet.occam.data.automata.transition_functions.transitions.output_config.IOutputInternConfiguration;

public interface ITransitionFunction<
	State extends IState<InputSymbol, InputConfig, OutputConfig, Transition>,
	InputSymbol extends ISymbol, 
	InputConfig extends IInputConfiguration<InputSymbol>, 
	OutputConfig extends IOutputInternConfiguration,
	Transition extends ITransition<InputSymbol, InputConfig, OutputConfig>> {
	
	Set<InputSymbol> getInputAlphabet();

	Collection<State> getStates();
	
	State getStartState();
	
	Collection<Transition> getTransitions();
	
	Collection<State> getAcceptStates();
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object other);

}
