package com.tregouet.occam.data.automata.transition_functions;

import java.util.Collection;
import java.util.Set;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.transitions.ITransition;
import com.tregouet.occam.data.automata.transitions.input_config.IInputConfiguration;
import com.tregouet.occam.data.automata.transitions.output_config.IOutputInternConfiguration;

public interface ITransitionFunction<
	State extends IState,
	InputSymbol extends ISymbol, 
	InputConfig extends IInputConfiguration<InputSymbol>, 
	OutputConfig extends IOutputInternConfiguration,
	Transition extends ITransition<InputConfig, OutputConfig, InputSymbol>> {
	
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
