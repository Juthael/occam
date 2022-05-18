package com.tregouet.occam.data.problem_space.states.transitions.impl;

import java.util.Arrays;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.TransitionType;
import com.tregouet.occam.data.problem_space.states.transitions.dimensions.This;
import com.tregouet.occam.data.problem_space.states.transitions.productions.impl.ContextualizedEpsilonProd;

public class SpontaneousTransition extends ConceptTransition implements IConceptTransition {

	public SpontaneousTransition(int inputStateID, int outputStateID) {
		super(new ConceptTransitionIC(inputStateID, new ContextualizedEpsilonProd(null, null), This.INSTANCE),
				new ConceptTransitionOIC(outputStateID, Arrays.asList(new AVariable[] { This.INSTANCE })));
	}

	@Override
	public TransitionType type() {
		return TransitionType.SPONTANEOUS;
	}

}
