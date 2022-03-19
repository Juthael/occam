package com.tregouet.occam.data.representations.properties.transitions;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.automata.transition_functions.transitions.IPushdownAutomatonTransition;

public interface IConceptTransition extends IPushdownAutomatonTransition<
	IContextualizedProduction, 
	AVariable,
	IConceptTransitionIC, 
	IConceptTransitionOIC> {
	
	void setSalience(Salience salience);
	
	Salience getSalience();
	
	TransitionType type();

}
