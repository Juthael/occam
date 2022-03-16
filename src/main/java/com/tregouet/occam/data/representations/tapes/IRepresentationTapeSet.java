package com.tregouet.occam.data.representations.tapes;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.automata.tapes.IInputTape;
import com.tregouet.occam.data.automata.tapes.IPushdownAutomatonTapeSet;
import com.tregouet.occam.data.representations.IConcept;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionOIC;

public interface IRepresentationTapeSet extends
		IPushdownAutomatonTapeSet<
			IContextualizedProduction, 
			AVariable, 
			IInputTape<IContextualizedProduction>, 
			IConceptTransitionIC, 
			IConceptTransitionOIC, 
			IConceptTransition, 
			IConcept> {
	
	@Override
	public IRepresentationTapeSet apply(IConceptTransition transition);

}
