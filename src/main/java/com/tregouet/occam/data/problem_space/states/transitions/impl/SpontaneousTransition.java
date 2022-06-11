package com.tregouet.occam.data.problem_space.states.transitions.impl;

import java.util.Arrays;

import com.tregouet.occam.data.logical_structures.lambda_terms.IBindings;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.impl.EpsilonApplication;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.TransitionType;
import com.tregouet.occam.data.problem_space.states.transitions.impl.stack_default.ThisBinding;

public class SpontaneousTransition extends ConceptTransition implements IConceptTransition {

	public SpontaneousTransition(int inputStateID, int outputStateID) {
		super(
				new ConceptTransitionIC(inputStateID, EpsilonApplication.INSTANCE, ThisBinding.INSTANCE),
				new ConceptTransitionOIC(outputStateID, Arrays.asList(new IBindings[] { ThisBinding.INSTANCE })));
	}

	@Override
	public TransitionType type() {
		return TransitionType.SPONTANEOUS;
	}

}
