package com.tregouet.occam.data.automata.tapes;

import java.util.Set;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.transitions.ITransition;
import com.tregouet.occam.data.automata.transitions.input_config.IInputConfiguration;
import com.tregouet.occam.data.automata.transitions.output_config.IOutputInternConfiguration;

public interface ITapeSets<
	InputSymbol extends ISymbol,
	InputConfig extends IInputConfiguration<InputSymbol>, 
	OutputConfig extends IOutputInternConfiguration, 
	Transition extends ITransition<InputSymbol, InputConfig, OutputConfig>, 
	State extends IState<InputSymbol, InputConfig, OutputConfig, Transition>,
	TapeSet extends ITapeSet<InputSymbol, InputConfig, OutputConfig, Transition, State, TapeSet>
	>
	extends Set<TapeSet> {

}
