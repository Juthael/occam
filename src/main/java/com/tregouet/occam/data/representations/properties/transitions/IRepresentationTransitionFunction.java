package com.tregouet.occam.data.representations.properties.transitions;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.automata.transition_functions.IPushdownAutomatonTF;
import com.tregouet.occam.data.representations.concepts.IConcept;

public interface IRepresentationTransitionFunction extends 
	IPushdownAutomatonTF<
		IConcept, 
		IContextualizedProduction, 
		AVariable, 
		IConceptTransitionIC, 
		IConceptTransitionOIC, 
		IConceptTransition> {

}
