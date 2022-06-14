package com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.impl;

import java.util.ArrayList;
import java.util.Arrays;

import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.impl.Denotation;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.impl.Concept;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.problem_space.states.productions.IProduction;
import com.tregouet.occam.data.problem_space.states.productions.impl.OmegaProd;

public class OmegaComputation extends Computation implements IComputation {

	public static final OmegaComputation INSTANCE = new OmegaComputation();

	private OmegaComputation() {
		super(null, null, new ArrayList<>(Arrays.asList(new IProduction[] {OmegaProd.INSTANCE})), 
				new Denotation(OmegaProd.INSTANCE.getValue(), Concept.WHAT_IS_THERE_ID));
	}

}
