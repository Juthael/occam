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
	State extends IState
	> {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);
	
	ITapeSet<InputSymbol, InputTape, InputConfig, OutputConfig, Transition, State> apply(Transition transition);
	
	void loadIntoNext(State state);
	
	ITapeSet<InputSymbol, InputTape, InputConfig, OutputConfig, Transition, State> clone();
	
}
