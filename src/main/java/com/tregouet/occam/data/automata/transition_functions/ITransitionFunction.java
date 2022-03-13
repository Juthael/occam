package com.tregouet.occam.data.automata.transition_functions;

import java.util.Set;

import org.jgrapht.graph.DirectedMultigraph;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.transitions.ITransition;
import com.tregouet.occam.data.automata.transitions.input_config.IInputConfiguration;
import com.tregouet.occam.data.automata.transitions.output_config.IOutputInternConfiguration;
import com.tregouet.occam.data.languages.IWord;

public interface ITransitionFunction<
	S extends IState,
	I extends ISymbol, 
	K extends ISymbol, 
	Input extends IInputConfiguration<I>, 
	Output extends IOutputInternConfiguration,
	T extends ITransition<Input, Output, I>> {
	
	Set<I> getInputAlphabet();
	
	Set<K> getStackAlphabet();
	
	Set<IWord<I>> getStateLanguage(int iD);
	
	Set<IWord<I>> getMachineLanguage();
	
	DirectedMultigraph<S, T> getTransitionFunctionMultiGraph();
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object other);

}
