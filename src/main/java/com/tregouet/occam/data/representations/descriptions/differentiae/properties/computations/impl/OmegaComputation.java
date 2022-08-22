package com.tregouet.occam.data.representations.descriptions.differentiae.properties.computations.impl;

import com.tregouet.occam.data.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.representations.classifications.concepts.denotations.impl.Denotation;
import com.tregouet.occam.data.representations.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.representations.descriptions.differentiae.properties.computations.abstr_app.impl.OmegaOperator;
import com.tregouet.occam.data.representations.productions.impl.OmegaProd;

public class OmegaComputation extends Computation implements IComputation {

	public static final OmegaComputation INSTANCE = new OmegaComputation();

	private OmegaComputation() {
		super(null, OmegaOperator.INSTANCE,
				new Denotation(OmegaProd.INSTANCE.getValue(), IConcept.WHAT_IS_THERE_ID));
	}

}
