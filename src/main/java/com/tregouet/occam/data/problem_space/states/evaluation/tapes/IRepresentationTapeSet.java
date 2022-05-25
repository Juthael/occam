package com.tregouet.occam.data.problem_space.states.evaluation.tapes;

import com.tregouet.occam.data.logical_structures.automata.tapes.IPushdownAutomatonTapeSet;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.transitions.productions.IContextualizedProduction;

public interface IRepresentationTapeSet
		extends IPushdownAutomatonTapeSet<IContextualizedProduction, AVariable, IRepresentationTapeSet> {

	@Override
	boolean equals(Object o);

	IFactTape getInputTape();

	@Override
	int hashCode();

	void input(IFactTape inputTape);

}
