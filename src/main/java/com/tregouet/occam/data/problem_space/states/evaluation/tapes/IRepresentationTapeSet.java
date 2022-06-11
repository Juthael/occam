package com.tregouet.occam.data.problem_space.states.evaluation.tapes;

import com.tregouet.occam.data.logical_structures.automata.tapes.IPushdownAutomatonTapeSet;
import com.tregouet.occam.data.logical_structures.lambda_terms.IBindings;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.IApplication;

public interface IRepresentationTapeSet
		extends IPushdownAutomatonTapeSet<IApplication, IBindings, IRepresentationTapeSet> {

	@Override
	boolean equals(Object o);

	IFactTape getInputTape();

	@Override
	int hashCode();

	void input(IFactTape inputTape);

}
