package com.tregouet.occam.data.representations.evaluation.head;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.automata.heads.IPushdownAutomatonHead;
import com.tregouet.occam.data.languages.specific.IFact;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.evaluation.tapes.IRepresentationTapeSet;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;

public interface IRepresentationHead extends
	IPushdownAutomatonHead<
		IRepresentationTransitionFunction, 
		IConcept, 
		IContextualizedProduction, 
		AVariable, 
		IConceptTransitionIC, 
		IConceptTransitionOIC, 
		IConceptTransition, 
		IRepresentationTapeSet, 
		IFact, 
		IRepresentationHead> {
	
}
