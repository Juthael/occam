package com.tregouet.occam.data.automata.states;

import java.util.Set;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.automata.transitions.ITransition;
import com.tregouet.occam.data.automata.transitions.input_config.IInputConfiguration;
import com.tregouet.occam.data.automata.transitions.output_config.IOutputInternConfiguration;

public interface IState<
	InputSymbol extends ISymbol,
	InputConfig extends IInputConfiguration<InputSymbol>, 
	OutputConfig extends IOutputInternConfiguration, 
	Transition extends ITransition<InputSymbol, InputConfig, OutputConfig>
	> {
	
	@Override
	boolean equals(Object o);
	
	int getRank();
	
	int iD();
	
	@Override
	int hashCode();
	
	void setRank(int rank);
	
	void init();
	
	void loadTransitionRule(Transition transition);
	
	void loadTransitionRules(Set<Transition> transitions);	

}
