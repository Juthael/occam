package com.tregouet.occam.data.representations.transitions;

import java.util.Set;

import com.tregouet.occam.data.logical_structures.automata.transition_functions.IPushdownAutomatonTF;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;

public interface IRepresentationTransitionFunction extends 
	IPushdownAutomatonTF<
		IContextualizedProduction, 
		AVariable, 
		IConceptTransitionIC, 
		IConceptTransitionOIC, 
		IConceptTransition> {
	
	Set<IApplication> getSalientApplications();

}
