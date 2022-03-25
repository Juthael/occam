package com.tregouet.occam.data.representations.properties.transitions;

import java.util.Set;

import com.tregouet.occam.data.languages.alphabets.domain_specific.IContextualizedProduction;
import com.tregouet.occam.data.languages.alphabets.generic.AVariable;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.IPushdownAutomatonTF;

public interface IRepresentationTransitionFunction extends 
	IPushdownAutomatonTF<
		IContextualizedProduction, 
		AVariable, 
		IConceptTransitionIC, 
		IConceptTransitionOIC, 
		IConceptTransition> {
	
	Set<IApplication> getSalientApplications();

}
