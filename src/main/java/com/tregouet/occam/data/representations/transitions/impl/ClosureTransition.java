package com.tregouet.occam.data.representations.transitions.impl;

import java.util.Arrays;

import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.transitions.TransitionType;
import com.tregouet.occam.data.representations.transitions.impl.stack_default.EpsilonBinding;
import com.tregouet.occam.data.structures.lambda_terms.IBindings;

public class ClosureTransition extends ConceptTransition implements IConceptTransition {

	public ClosureTransition(IConceptTransitionIC inputConfig, int outputStateID) {
		super(inputConfig,
				new ConceptTransitionOIC(outputStateID, Arrays.asList(new IBindings[] { EpsilonBinding.INSTANCE })));
	}

	@Override
	public TransitionType type() {
		return TransitionType.CLOSURE;
	}

}
