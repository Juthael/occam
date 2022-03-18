package com.tregouet.occam.data.automata.heads;

import java.util.Set;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.tapes.ITapeSet;
import com.tregouet.occam.data.automata.transition_functions.ITransitionFunction;
import com.tregouet.occam.data.automata.transition_functions.transitions.ITransition;
import com.tregouet.occam.data.automata.transition_functions.transitions.input_config.IInputConfiguration;
import com.tregouet.occam.data.automata.transition_functions.transitions.output_config.IOutputInternConfiguration;
import com.tregouet.occam.data.languages.IWord;

public interface IHead<
	TransFunc extends ITransitionFunction<State, InputSymbol, InputConfig, OutputConfig, Transition>,
	TapeSet extends ITapeSet<InputSymbol, InputConfig, OutputConfig, Transition, State, TapeSet>,
	InputSymbol extends ISymbol,
	Word extends IWord<InputSymbol>,
	InputConfig extends IInputConfiguration<InputSymbol>,
	OutputConfig extends IOutputInternConfiguration,
	Transition extends ITransition<InputSymbol, InputConfig, OutputConfig>,
	State extends IState<InputSymbol, InputConfig, OutputConfig, Transition>,
	Head extends IHead<TransFunc, TapeSet, InputSymbol, Word, InputConfig, OutputConfig, Transition, State, Head>
	>{
	
	void input(Word word);
	
	void set(TransFunc transitionFunction, TapeSet tapeSet);
	
	Set<? extends Head> evaluateNextSymbol();
	
	Set<? extends Head> enumerateNextSymbol();
	
	boolean halted();
	
	boolean accepts();
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object o);

}
