package com.tregouet.occam.data.automata.machines;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.transition_functions.IPushdownAutomatonTF;
import com.tregouet.occam.data.automata.transitions.IPushdownAutomatonTransition;
import com.tregouet.occam.data.automata.transitions.input_config.IPushdownAutomatonIC;
import com.tregouet.occam.data.automata.transitions.output_config.IPushdownAutomatonOIC;

public interface IPushdownAutomaton<
	State extends IState, 
	InputSymbol extends ISymbol,
	StackSymbol extends ISymbol, 
	InputConfig extends IPushdownAutomatonIC<InputSymbol, StackSymbol>,
	OutputConfig extends IPushdownAutomatonOIC<StackSymbol>, 
	Transition extends IPushdownAutomatonTransition<InputSymbol,StackSymbol,InputConfig,OutputConfig>,
	TransFunc extends IPushdownAutomatonTF<
		State, 
		InputSymbol, 
		StackSymbol, 
		InputConfig, 
		OutputConfig, 
		Transition
		>
	>{

}
