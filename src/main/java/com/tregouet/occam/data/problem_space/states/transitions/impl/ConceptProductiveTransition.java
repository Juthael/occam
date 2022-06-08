package com.tregouet.occam.data.problem_space.states.transitions.impl;

import com.tregouet.occam.data.problem_space.states.transitions.IConceptProductiveTransition;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.problem_space.states.transitions.TransitionType;

public class ConceptProductiveTransition extends ConceptTransition implements IConceptProductiveTransition {

	public ConceptProductiveTransition(IConceptTransitionIC inputConfig, IConceptTransitionOIC outputInternConfig) {
		super(inputConfig, outputInternConfig);
	}

	@Override
	public TransitionType type() {
		return TransitionType.PRODUCTIVE_TRANS;
	}

}
