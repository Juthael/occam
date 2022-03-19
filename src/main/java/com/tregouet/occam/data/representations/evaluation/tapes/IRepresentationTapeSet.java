package com.tregouet.occam.data.representations.evaluation.tapes;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.automata.tapes.IPushdownAutomatonTapeSet;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransitionOIC;

public interface IRepresentationTapeSet extends
		IPushdownAutomatonTapeSet<
			IContextualizedProduction, 
			AVariable, 
			IConceptTransitionIC, 
			IConceptTransitionOIC, 
			IConceptTransition, 
			IConcept, 
			IRepresentationTapeSet> {
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object o);

}
