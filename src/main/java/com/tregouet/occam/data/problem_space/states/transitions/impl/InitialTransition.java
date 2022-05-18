package com.tregouet.occam.data.problem_space.states.transitions.impl;

import java.util.ArrayList;
import java.util.Arrays;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.concepts.impl.Everything;
import com.tregouet.occam.data.problem_space.states.concepts.impl.WhatIsThere;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.TransitionType;
import com.tregouet.occam.data.problem_space.states.transitions.dimensions.Nothing;
import com.tregouet.occam.data.problem_space.states.transitions.dimensions.This;
import com.tregouet.occam.data.problem_space.states.transitions.productions.impl.ContextualizedEpsilonProd;

public class InitialTransition extends ConceptTransition implements IConceptTransition {

	public InitialTransition(Everything everything) {
		super(new ConceptTransitionIC(WhatIsThere.INSTANCE.iD(), new ContextualizedEpsilonProd(null, null),
				Nothing.INSTANCE),
				new ConceptTransitionOIC(everything.iD(),
						new ArrayList<>(Arrays.asList(new AVariable[] { Nothing.INSTANCE, This.INSTANCE }))));
	}

	@Override
	public TransitionType type() {
		return TransitionType.INITIAL;
	}

}
