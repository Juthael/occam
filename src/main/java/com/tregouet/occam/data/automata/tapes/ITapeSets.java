package com.tregouet.occam.data.automata.tapes;

import java.util.Set;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.transitions.ITransition;
import com.tregouet.occam.data.automata.transitions.input_config.IInputConfiguration;
import com.tregouet.occam.data.automata.transitions.output_config.IOutputInternConfiguration;

public interface ITapeSets<
	InputSymbol extends ISymbol,
	InputTape extends IInputTape<InputSymbol>, 
	InputConfig extends IInputConfiguration<InputSymbol>, 
	OutputConfig extends IOutputInternConfiguration, 
	Transition extends ITransition<InputConfig, OutputConfig, InputSymbol>, 
	State extends IState,
	TapeSet extends ITapeSet<InputSymbol, InputTape, InputConfig, OutputConfig, Transition, State, TapeSet>
	>
	extends Set<TapeSet> {

}
