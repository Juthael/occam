package com.tregouet.occam.data.representations.evaluation.tapes;

import com.tregouet.occam.data.languages.alphabets.domain_specific.IContextualizedProduction;
import com.tregouet.occam.data.languages.alphabets.generic.AVariable;
import com.tregouet.occam.data.languages.words.fact.IFact;
import com.tregouet.occam.data.logical_structures.automata.tapes.IPushdownAutomatonTapeSet;

public interface IRepresentationTapeSet extends
		IPushdownAutomatonTapeSet<
			IContextualizedProduction, 
			AVariable, 
			IRepresentationTapeSet> {
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object o);
	
	IFact getInputTape();

}
