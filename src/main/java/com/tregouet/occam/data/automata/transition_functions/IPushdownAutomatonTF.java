package com.tregouet.occam.data.automata.transition_functions;

import java.util.Set;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.automata.states.IPushdownAutomatonState;
import com.tregouet.occam.data.automata.transition_functions.transitions.IPushdownAutomatonTransition;
import com.tregouet.occam.data.automata.transition_functions.transitions.input_config.IPushdownAutomatonIC;
import com.tregouet.occam.data.automata.transition_functions.transitions.output_config.IPushdownAutomatonOIC;

public interface IPushdownAutomatonTF<
	State extends IPushdownAutomatonState<InputSymbol, StackSymbol, InputConfig, OutputConfig, Transition>, 
	InputSymbol extends ISymbol, 
	StackSymbol extends ISymbol, 
	InputConfig extends IPushdownAutomatonIC<InputSymbol, StackSymbol>,
	OutputConfig extends IPushdownAutomatonOIC<StackSymbol>,
	Transition extends IPushdownAutomatonTransition<InputSymbol, StackSymbol, InputConfig, OutputConfig>
	>
	extends ITransitionFunction<State, InputSymbol, InputConfig, OutputConfig, Transition> {
	
	Set<StackSymbol> getStackAlphabet();
	
	StackSymbol getInitialStackSymbol();
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object o);

}
