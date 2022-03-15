package com.tregouet.occam.data.representations.transitions;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.automata.transitions.IPushdownAutomatonTransition;

public interface IConceptTransition extends IPushdownAutomatonTransition<
	IContextualizedProduction, 
	AVariable,
	IConceptTransitionIC, 
	IConceptTransitionOIC> {
	
	void setSalience(Salience salience);
	
	Salience getSalience();

}
