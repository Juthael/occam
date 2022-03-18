package com.tregouet.occam.data.automata;

import java.util.Collection;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.transition_functions.ITransitionFunction;
import com.tregouet.occam.data.automata.transition_functions.transitions.ITransition;
import com.tregouet.occam.data.automata.transition_functions.transitions.input_config.IInputConfiguration;
import com.tregouet.occam.data.automata.transition_functions.transitions.output_config.IOutputInternConfiguration;

public interface IAutomaton<
	State extends IState<InputSymbol, InputConfig, OutputConfig, Transition>, 
	InputSymbol extends ISymbol,
	InputConfig extends IInputConfiguration<InputSymbol>,
	OutputConfig extends IOutputInternConfiguration,
	Transition extends ITransition<InputSymbol, InputConfig, OutputConfig>,
	TransFunc extends ITransitionFunction<State, InputSymbol, InputConfig, OutputConfig, Transition>
	>{
	
	@Override
	boolean equals(Object o);
	
	Collection<State> getStates();
	
	TransFunc getTransitionFunction();
	
	State getStartState();
	
	AVariable getInitialStackSymbol();
	
	Collection<State> getAcceptStates();
	
	State getStateWithID(int iD);
	
	@Override
	int hashCode();
	
}
