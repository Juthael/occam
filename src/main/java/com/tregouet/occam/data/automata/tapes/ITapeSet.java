package com.tregouet.occam.data.automata.tapes;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.transitions.ITransition;
import com.tregouet.occam.data.automata.transitions.input_config.IInputConfiguration;
import com.tregouet.occam.data.automata.transitions.output_config.IOutputInternConfiguration;

public interface ITapeSet<
	InputSymbol extends ISymbol, 
	InputTape extends IInputTape<InputSymbol>,
	InputConfig extends IInputConfiguration<InputSymbol>, 
	OutputConfig extends IOutputInternConfiguration,
	Transition extends ITransition<InputConfig, OutputConfig, InputSymbol>, 
	State extends IState,
	TapeSet extends ITapeSet<InputSymbol, InputTape, InputConfig, OutputConfig, Transition, State, TapeSet>
	> {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);
	
	TapeSet proceed(Transition transition);
	
	TapeSet copy();
	
	boolean accepted();
	
	State acceptedBy(State acceptState);
	
	ITapeSets<InputSymbol, InputTape, InputConfig,OutputConfig, Transition, State, TapeSet> evaluate();
	
}
