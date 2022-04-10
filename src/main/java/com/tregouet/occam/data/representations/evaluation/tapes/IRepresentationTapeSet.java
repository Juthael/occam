package com.tregouet.occam.data.representations.evaluation.tapes;

import com.tregouet.occam.data.logical_structures.automata.tapes.IPushdownAutomatonTapeSet;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;

public interface IRepresentationTapeSet extends
		IPushdownAutomatonTapeSet<
			IContextualizedProduction, 
			AVariable, 
			IRepresentationTapeSet> {
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object o);
	
	IFactTape getInputTape();
	
	void input(IFactTape inputTape);

}
