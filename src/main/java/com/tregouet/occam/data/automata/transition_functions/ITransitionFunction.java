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
	State extends IState,
	InputSymbol extends ISymbol, 
	InputConfig extends IInputConfiguration<InputSymbol>, 
	OutputConfig extends IOutputInternConfiguration,
	T extends ITransition<InputConfig, OutputConfig, InputSymbol>> {
	
	Set<InputSymbol> getInputAlphabet();
	
	
	
	Set<IWord<InputSymbol>> getStateLanguage(int iD);
	
	Set<IWord<InputSymbol>> getMachineLanguage();
	
	DirectedMultigraph<State, T> getTransitionFunctionMultiGraph();
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object other);

}
