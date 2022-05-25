package com.tregouet.occam.data.logical_structures.automata;

import com.tregouet.occam.data.logical_structures.automata.states.IState;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.IPushdownAutomatonTF;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.IPushdownAutomatonTransition;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.input_config.IPushdownAutomatonIC;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.output_config.IPushdownAutomatonOIC;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.logical_structures.languages.words.IWord;

public interface IPushdownAutomaton<State extends IState, InputSymbol extends ISymbol, Word extends IWord<InputSymbol>, StackSymbol extends ISymbol, InputConfig extends IPushdownAutomatonIC<InputSymbol, StackSymbol>, OutputConfig extends IPushdownAutomatonOIC<StackSymbol>, Transition extends IPushdownAutomatonTransition<InputSymbol, StackSymbol, InputConfig, OutputConfig>, TransFunc extends IPushdownAutomatonTF<InputSymbol, StackSymbol, InputConfig, OutputConfig, Transition>>
		extends IAutomaton<State, InputSymbol, Word, InputConfig, OutputConfig, Transition, TransFunc> {

	@Override
	boolean equals(Object o);

	@Override
	int hashCode();

}
