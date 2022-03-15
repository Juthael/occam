package com.tregouet.occam.data.automata.machines;

import java.util.Collection;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.transition_functions.ITransitionFunction;
import com.tregouet.occam.data.automata.transitions.ITransition;
import com.tregouet.occam.data.automata.transitions.input_config.IInputConfiguration;
import com.tregouet.occam.data.automata.transitions.output_config.IOutputInternConfiguration;

public interface IAutomaton<
	State extends IState, 
	InputSymbol extends ISymbol,
	InputConfig extends IInputConfiguration<InputSymbol>,
	OutputConfig extends IOutputInternConfiguration,
	TransFunc extends ITransitionFunction<
		State, 
		InputSymbol, 
		InputConfig, 
		OutputConfig, 
		ITransition<InputConfig,OutputConfig,InputSymbol>>
	>{
	
	@Override
	boolean equals(Object o);
	
	Collection<IState> getStates();
	
	TransFunc getTransitionFunction();
	
	IState getStartState();
	
	AVariable getInitialStackSymbol();
	
	Collection<IState> getAcceptStates();
	
	IState getStateWithID(int iD);
	
	@Override
	int hashCode();
	
}
