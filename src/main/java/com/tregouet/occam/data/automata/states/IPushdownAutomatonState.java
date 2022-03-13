package com.tregouet.occam.data.automata.states;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.automata.transitions.IPushdownAutomatonTransition;
import com.tregouet.occam.data.automata.transitions.input_config.IPushdownAutomatonIC;
import com.tregouet.occam.data.automata.transitions.output_config.IPushdownAutomatonOIC;

public interface IPushdownAutomatonState<
	InputSymbol extends ISymbol, 
	StackSymbol extends ISymbol, 
	InputConfiguration extends IPushdownAutomatonIC<InputSymbol, StackSymbol>,
	OutputConfiguration extends IPushdownAutomatonOIC<StackSymbol>,
	Transition extends IPushdownAutomatonTransition<InputSymbol, StackSymbol, InputConfiguration, OutputConfiguration>
	>
	extends IState {
	
	void loadTransitionRules(IPushdownAutomatonTransition<InputSymbol, StackSymbol, InputConfiguration, OutputConfiguration> transitions);

}
