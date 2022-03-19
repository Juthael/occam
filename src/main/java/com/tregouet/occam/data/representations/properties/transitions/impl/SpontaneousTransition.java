package com.tregouet.occam.data.representations.properties.transitions.impl;

import java.util.Arrays;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.alphabets.productions.impl.ContextualizedEpsilonProd;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.properties.transitions.TransitionType;
import com.tregouet.occam.data.representations.properties.transitions.dimensions.This;

public class SpontaneousTransition extends ConceptTransition implements IConceptTransition {

	public SpontaneousTransition(int inputStateID, int outputStateID) {
		super(new ConceptTransitionIC(inputStateID, new ContextualizedEpsilonProd(null, null), This.INSTANCE), 
				new ConceptTransitionOIC(outputStateID, Arrays.asList(new AVariable[] {This.INSTANCE})));
	}

	@Override
	public TransitionType type() {
		return TransitionType.SPONTANEOUS;
	}

}
