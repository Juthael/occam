package com.tregouet.occam.data.representations.tapes;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.automata.tapes.IPushdownAutomatonTapeSet;
import com.tregouet.occam.data.languages.specific.IFact;
import com.tregouet.occam.data.representations.IConcept;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionOIC;

public interface IRepresentationTapeSet extends
		IPushdownAutomatonTapeSet<
			IContextualizedProduction, 
			AVariable, 
			IFact, 
			IConceptTransitionIC, 
			IConceptTransitionOIC, 
			IConceptTransition, 
			IConcept, 
			IRepresentationTapeSet> {
	
	@Override
	public IRepresentationTapeSet proceed(IConceptTransition transition);
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object o);
	
	@Override
	IRepresentationTapeSets evaluate();	

}