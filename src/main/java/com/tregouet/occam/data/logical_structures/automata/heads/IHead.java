package com.tregouet.occam.data.logical_structures.automata.heads;

import java.util.Set;

import com.tregouet.occam.data.languages.alphabets.ISymbol;
import com.tregouet.occam.data.languages.words.IWord;
import com.tregouet.occam.data.logical_structures.automata.tapes.ITapeSet;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.ITransitionFunction;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.ITransition;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.input_config.IInputConfiguration;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.output_config.IOutputInternConfiguration;

public interface IHead<
	TransFunc extends ITransitionFunction<InputSymbol, InputConfig, OutputConfig, Transition>,
	TapeSet extends ITapeSet<InputSymbol, TapeSet>,
	InputSymbol extends ISymbol,
	Word extends IWord<InputSymbol>,
	InputConfig extends IInputConfiguration<InputSymbol>,
	OutputConfig extends IOutputInternConfiguration,
	Transition extends ITransition<InputSymbol, InputConfig, OutputConfig>,
	Head extends IHead<TransFunc, TapeSet, InputSymbol, Word, InputConfig, OutputConfig, Transition, Head>
	>{
	
	IHead<TransFunc, TapeSet, InputSymbol, Word, InputConfig, OutputConfig, Transition, Head> input(Word word);
	
	void set(TransFunc transitionFunction, TapeSet tapeSet);
	
	Set<Head> evaluate();
	
	Set<Head> factEnumerator();
	
	boolean halted();
	
	boolean accepts();
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object o);
	
	void reinitialize();

}
