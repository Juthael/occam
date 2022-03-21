package com.tregouet.occam.data.representations.properties.transitions;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.automata.transition_functions.IPushdownAutomatonTF;

public interface IRepresentationTransitionFunction extends 
	IPushdownAutomatonTF<
		IContextualizedProduction, 
		AVariable, 
		IConceptTransitionIC, 
		IConceptTransitionOIC, 
		IConceptTransition> {

}
