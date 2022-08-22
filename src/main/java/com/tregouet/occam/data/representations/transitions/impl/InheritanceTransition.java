package com.tregouet.occam.data.representations.transitions.impl;

import java.util.Arrays;

import com.tregouet.occam.data.representations.descriptions.differentiae.properties.computations.abstr_app.impl.EpsilonOperator;
import com.tregouet.occam.data.representations.descriptions.differentiae.properties.computations.impl.EpsilonComputation;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.TransitionType;
import com.tregouet.occam.data.representations.transitions.impl.stack_default.NothingBinding;
import com.tregouet.occam.data.structures.lambda_terms.IBindings;

public class InheritanceTransition extends ConceptTransition implements IConceptTransition {

	EpsilonComputation app;

	// for inheritance of closed denotations
	public InheritanceTransition(int inputStateID, int outputStateID) {
		super(
				new ConceptTransitionIC(
						inputStateID, EpsilonOperator.INSTANCE, NothingBinding.INSTANCE),
				new ConceptTransitionOIC(outputStateID, Arrays.asList(new IBindings[] { NothingBinding.INSTANCE })));
	}

	// for inheritance of unclosed denotations.
	public InheritanceTransition(int inputStateID, int outputStateID, IBindings variable) {
		super(new ConceptTransitionIC(inputStateID, EpsilonOperator.INSTANCE, variable),
				new ConceptTransitionOIC(outputStateID, Arrays.asList(new IBindings[] { variable })));
	}

	@Override
	public TransitionType type() {
		return TransitionType.INHERITANCE;
	}

}
