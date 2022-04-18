package com.tregouet.occam.data.logical_structures.automata;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.data.logical_structures.automata.states.IState;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.ITransitionFunction;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.ITransition;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.input_config.IInputConfiguration;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.output_config.IOutputInternConfiguration;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.logical_structures.languages.words.IWord;

public interface IAutomaton<
	State extends IState,
	InputSymbol extends ISymbol,
	Word extends IWord<InputSymbol>,
	InputConfig extends IInputConfiguration<InputSymbol>,
	OutputConfig extends IOutputInternConfiguration,
	Transition extends ITransition<InputSymbol, InputConfig, OutputConfig>,
	TransFunc extends ITransitionFunction<InputSymbol, InputConfig, OutputConfig, Transition>
	>{

	Set<Word> enumerateMachineLanguage();

	@Override
	boolean equals(Object o);

	boolean evaluate(Word word);

	Collection<State> getStates();

	TransFunc getTransitionFunction();

	@Override
	int hashCode();

	Map<State, Set<Word>> mapAcceptStateToAcceptedWords();

}
