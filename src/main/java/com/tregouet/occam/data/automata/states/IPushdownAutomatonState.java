package com.tregouet.occam.data.automata.states;

import java.util.Set;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.automata.transition_functions.transitions.IPushdownAutomatonTransition;
import com.tregouet.occam.data.automata.transition_functions.transitions.input_config.IPushdownAutomatonIC;
import com.tregouet.occam.data.automata.transition_functions.transitions.output_config.IPushdownAutomatonOIC;

public interface IPushdownAutomatonState<
	InputSymbol extends ISymbol, 
	StackSymbol extends ISymbol,  
	InputConfig extends IPushdownAutomatonIC<InputSymbol, StackSymbol>, 
	OutputConfig extends IPushdownAutomatonOIC<StackSymbol>, 
	Transition extends IPushdownAutomatonTransition<InputSymbol, StackSymbol, InputConfig, OutputConfig>
	>
	extends IState<InputSymbol, InputConfig, OutputConfig, Transition>{
	
	void loadTransitionRule(Transition transition);
	
	void loadTransitionRules(Set<Transition> transitions);
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object o);

}
