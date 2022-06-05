package com.tregouet.occam.data.problem_space.states.transitions.impl;

import com.tregouet.occam.data.problem_space.states.transitions.IApplication;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.problem_space.states.transitions.TransitionType;

public class Application extends ConceptTransition implements IApplication {

	public Application(IConceptTransitionIC inputConfig, IConceptTransitionOIC outputInternConfig) {
		super(inputConfig, outputInternConfig);
	}

	@Override
	public TransitionType type() {
		return TransitionType.APPLICATION;
	}

}
