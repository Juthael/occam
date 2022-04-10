package com.tregouet.occam.data.representations.transitions;

import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.IPushdownAutomatonTransition;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;

public interface IConceptTransition extends IPushdownAutomatonTransition<
	IContextualizedProduction, 
	AVariable,
	IConceptTransitionIC, 
	IConceptTransitionOIC> {
	
	void setSalience(Salience salience);
	
	Salience getSalience();
	
	TransitionType type();

}
