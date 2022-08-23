package com.tregouet.occam.data.structures.representations.evaluation.tapes;

import com.tregouet.occam.data.structures.automata.tapes.IPushdownAutomatonTapeSet;
import com.tregouet.occam.data.structures.lambda_terms.IBindings;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.abstr_app.IAbstractionApplication;

public interface IRepresentationTapeSet
		extends IPushdownAutomatonTapeSet<IAbstractionApplication, IBindings, IRepresentationTapeSet> {

	@Override
	boolean equals(Object o);

	IFactTape getInputTape();

	@Override
	int hashCode();

	void input(IFactTape inputTape);

}
