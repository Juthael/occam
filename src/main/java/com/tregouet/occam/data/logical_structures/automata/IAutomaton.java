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
	
	@Override
	boolean equals(Object o);
	
	Collection<State> getStates();
	
	TransFunc getTransitionFunction();
	
	boolean evaluate(Word word);
	
	Set<Word> enumerateMachineLanguage();
	
	Map<State, Set<Word>> mapAcceptStateToAcceptedWords();
	
	@Override
	int hashCode();
	
}
