package com.tregouet.occam.data.representations.transitions.impl;

import java.util.Arrays;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.dimensions.Nothing;

public class ClosureTransition extends ConceptTransition implements IConceptTransition {

	public ClosureTransition(ConceptTransitionIC inputConfig, int outputStateID) {
		super(inputConfig, new ConceptTransitionOIC(outputStateID, Arrays.asList(new AVariable[] {Nothing.INSTANCE})));
	}

}
