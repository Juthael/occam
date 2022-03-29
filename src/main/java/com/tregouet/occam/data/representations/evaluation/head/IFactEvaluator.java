package com.tregouet.occam.data.representations.evaluation.head;

import com.tregouet.occam.data.languages.alphabets.domain_specific.IContextualizedProduction;
import com.tregouet.occam.data.languages.alphabets.generic.AVariable;
import com.tregouet.occam.data.languages.words.fact.IFact;
import com.tregouet.occam.data.logical_structures.automata.heads.IPushdownAutomatonHead;
import com.tregouet.occam.data.representations.evaluation.tapes.IRepresentationTapeSet;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;

public interface IFactEvaluator extends
	IPushdownAutomatonHead<
		IRepresentationTransitionFunction, 
		IContextualizedProduction, 
		AVariable, 
		IConceptTransitionIC, 
		IConceptTransitionOIC, 
		IConceptTransition, 
		IRepresentationTapeSet, 
		IFact, 
		IFactEvaluator> {
	
	IRepresentationTransitionFunction getTransitionFunction();
	
	IRepresentationTapeSet getTapeSet();
	
}
