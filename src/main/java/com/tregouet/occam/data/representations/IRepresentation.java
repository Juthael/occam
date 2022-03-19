package com.tregouet.occam.data.representations;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.automata.IPushdownAutomaton;
import com.tregouet.occam.data.languages.specific.IFact;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;

public interface IRepresentation extends 
	IPushdownAutomaton<
		IConcept, 
		IContextualizedProduction, 
		IFact,
		AVariable, 
		IConceptTransitionIC, 
		IConceptTransitionOIC, 
		IConceptTransition,
		IRepresentationTransitionFunction
	> {

}
