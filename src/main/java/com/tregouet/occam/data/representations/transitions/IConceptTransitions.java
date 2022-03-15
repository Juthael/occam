package com.tregouet.occam.data.representations.transitions;

import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.automata.transition_functions.IPushdownAutomatonTF;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.representations.IConcept;

public interface IConceptTransitions extends 
	IPushdownAutomatonTF<
		IConcept, 
		IContextualizedProduction, 
		AVariable, 
		IConceptTransitionIC, 
		IConceptTransitionOIC, 
		IConceptTransition> {

}
