package com.tregouet.occam.data.automata.heads;

import java.util.Set;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.automata.states.IPushdownAutomatonState;
import com.tregouet.occam.data.automata.tapes.IPushdownAutomatonTapeSet;
import com.tregouet.occam.data.automata.transition_functions.IPushdownAutomatonTF;
import com.tregouet.occam.data.automata.transition_functions.transitions.IPushdownAutomatonTransition;
import com.tregouet.occam.data.automata.transition_functions.transitions.input_config.IPushdownAutomatonIC;
import com.tregouet.occam.data.automata.transition_functions.transitions.output_config.IPushdownAutomatonOIC;
import com.tregouet.occam.data.languages.IWord;

public interface IPushdownAutomatonHead<
	TransitionFunc extends IPushdownAutomatonTF<State, InputSymbol, StackSymbol, InputConfig, OutputConfig, Transition>,
	State extends IPushdownAutomatonState<InputSymbol, StackSymbol, InputConfig, OutputConfig, Transition>,
	InputSymbol extends ISymbol,
	StackSymbol extends ISymbol,
	InputConfig extends IPushdownAutomatonIC<InputSymbol, StackSymbol>,
	OutputConfig extends IPushdownAutomatonOIC<StackSymbol>,
	Transition extends IPushdownAutomatonTransition<InputSymbol, StackSymbol, InputConfig, OutputConfig>,
	TapeSet extends IPushdownAutomatonTapeSet<InputSymbol, StackSymbol, InputConfig, OutputConfig, Transition, State, TapeSet>, 
	Word extends IWord<InputSymbol>, 
	Head extends IHead<TransitionFunc, TapeSet, InputSymbol, Word, InputConfig, OutputConfig, Transition, State, Head>
> extends IHead<TransitionFunc, TapeSet, InputSymbol, Word, InputConfig, OutputConfig, Transition, State, Head> {
	
	@Override
	Set<? extends Head> evaluate();
	
	@Override
	Set<? extends Head> enumerate();

}
