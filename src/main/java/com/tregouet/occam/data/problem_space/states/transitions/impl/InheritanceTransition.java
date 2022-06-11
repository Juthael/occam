package com.tregouet.occam.data.problem_space.states.transitions.impl;

import java.util.Arrays;

import com.tregouet.occam.data.logical_structures.lambda_terms.IBindings;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.impl.EpsilonApplication;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.TransitionType;
import com.tregouet.occam.data.problem_space.states.transitions.impl.stack_default.NothingBinding;

public class InheritanceTransition extends ConceptTransition implements IConceptTransition {
	
	EpsilonApplication app;

	// for inheritance of unclosed denotations.
	public InheritanceTransition(int inputStateID, int outputStateID, IBindings variable) {
		super(new ConceptTransitionIC(inputStateID, EpsilonApplication.INSTANCE, variable),
				new ConceptTransitionOIC(outputStateID, Arrays.asList(new IBindings[] { variable })));
	}

	// for inheritance of closed denotations
	public InheritanceTransition(int inputStateID, int outputStateID) {
		super(
				new ConceptTransitionIC(
						inputStateID, EpsilonApplication.INSTANCE, NothingBinding.INSTANCE),
				new ConceptTransitionOIC(outputStateID, Arrays.asList(new IBindings[] { NothingBinding.INSTANCE })));
	}

	@Override
	public TransitionType type() {
		return TransitionType.INHERITANCE;
	}

}
