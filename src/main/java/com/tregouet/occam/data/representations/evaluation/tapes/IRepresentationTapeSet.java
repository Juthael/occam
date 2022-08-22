package com.tregouet.occam.data.representations.evaluation.tapes;

import com.tregouet.occam.data.representations.descriptions.differentiae.properties.computations.abstr_app.IAbstractionApplication;
import com.tregouet.occam.data.structures.automata.tapes.IPushdownAutomatonTapeSet;
import com.tregouet.occam.data.structures.lambda_terms.IBindings;

public interface IRepresentationTapeSet
		extends IPushdownAutomatonTapeSet<IAbstractionApplication, IBindings, IRepresentationTapeSet> {

	@Override
	boolean equals(Object o);

	IFactTape getInputTape();

	@Override
	int hashCode();

	void input(IFactTape inputTape);

}
