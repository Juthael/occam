package com.tregouet.occam.data.representations.transitions.impl;

import java.util.Arrays;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.transitions.TransitionType;
import com.tregouet.occam.data.representations.transitions.dimensions.EpsilonDimension;

public class ClosureTransition extends ConceptTransition implements IConceptTransition {

	public ClosureTransition(IConceptTransitionIC inputConfig, int outputStateID) {
		super(inputConfig, new ConceptTransitionOIC(outputStateID, Arrays.asList(new AVariable[] {EpsilonDimension.INSTANCE})));
	}

	@Override
	public TransitionType type() {
		return TransitionType.CLOSURE;
	}

}
