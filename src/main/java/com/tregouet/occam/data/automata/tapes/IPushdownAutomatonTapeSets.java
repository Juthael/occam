package com.tregouet.occam.data.automata.tapes;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.automata.states.IPushdownAutomatonState;
import com.tregouet.occam.data.automata.transitions.IPushdownAutomatonTransition;
import com.tregouet.occam.data.automata.transitions.input_config.IPushdownAutomatonIC;
import com.tregouet.occam.data.automata.transitions.output_config.IPushdownAutomatonOIC;

public interface IPushdownAutomatonTapeSets<
	InputSymbol extends ISymbol, 
	StackSymbol extends ISymbol, 
	InputConfig extends IPushdownAutomatonIC<InputSymbol, StackSymbol>, 
	OutputConfig extends IPushdownAutomatonOIC<StackSymbol>, 
	Transition extends IPushdownAutomatonTransition<InputSymbol, StackSymbol, InputConfig, OutputConfig>, 
	State extends IPushdownAutomatonState<InputSymbol, StackSymbol, InputConfig, OutputConfig, Transition>, 
	TapeSet extends IPushdownAutomatonTapeSet<
		InputSymbol, StackSymbol, InputConfig, OutputConfig, Transition, State, TapeSet>
	>
	extends ITapeSets<InputSymbol, InputConfig, OutputConfig, Transition, State, TapeSet> {

}
