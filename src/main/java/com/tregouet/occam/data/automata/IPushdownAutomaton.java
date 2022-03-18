package com.tregouet.occam.data.automata;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.automata.states.IPushdownAutomatonState;
import com.tregouet.occam.data.automata.transition_functions.IPushdownAutomatonTF;
import com.tregouet.occam.data.automata.transition_functions.transitions.IPushdownAutomatonTransition;
import com.tregouet.occam.data.automata.transition_functions.transitions.input_config.IPushdownAutomatonIC;
import com.tregouet.occam.data.automata.transition_functions.transitions.output_config.IPushdownAutomatonOIC;
import com.tregouet.occam.data.languages.IWord;

public interface IPushdownAutomaton<
	State extends IPushdownAutomatonState<InputSymbol, StackSymbol, InputConfig, OutputConfig, Transition>, 
	InputSymbol extends ISymbol,
	Word extends IWord<InputSymbol>,
	StackSymbol extends ISymbol, 
	InputConfig extends IPushdownAutomatonIC<InputSymbol, StackSymbol>,
	OutputConfig extends IPushdownAutomatonOIC<StackSymbol>, 
	Transition extends IPushdownAutomatonTransition<InputSymbol, StackSymbol, InputConfig, OutputConfig>,
	TransFunc extends IPushdownAutomatonTF<State, InputSymbol, StackSymbol, InputConfig, OutputConfig, Transition>
	> 
	extends IAutomaton<State, InputSymbol, Word, InputConfig, OutputConfig, Transition, TransFunc>{
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object o);

}
