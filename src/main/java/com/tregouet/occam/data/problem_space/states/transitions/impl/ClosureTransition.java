package com.tregouet.occam.data.problem_space.states.transitions.impl;

import java.util.Arrays;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.problem_space.states.transitions.TransitionType;
import com.tregouet.occam.data.problem_space.states.transitions.dimensions.EpsilonDimension;

public class ClosureTransition extends ConceptTransition implements IConceptTransition {

	public ClosureTransition(IConceptTransitionIC inputConfig, int outputStateID) {
		super(inputConfig,
				new ConceptTransitionOIC(outputStateID, Arrays.asList(new AVariable[] { EpsilonDimension.INSTANCE })));
	}

	@Override
	public TransitionType type() {
		return TransitionType.CLOSURE;
	}

}
