package com.tregouet.occam.data.structures.representations.transitions.impl;

import com.tregouet.occam.data.structures.representations.transitions.IConceptProductiveTransition;
import com.tregouet.occam.data.structures.representations.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.structures.representations.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.structures.representations.transitions.TransitionType;

public class ConceptProductiveTransition extends ConceptTransition implements IConceptProductiveTransition {

	public ConceptProductiveTransition(IConceptTransitionIC inputConfig, IConceptTransitionOIC outputInternConfig) {
		super(inputConfig, outputInternConfig);
	}

	@Override
	public TransitionType type() {
		return TransitionType.PRODUCTIVE_TRANS;
	}

}
