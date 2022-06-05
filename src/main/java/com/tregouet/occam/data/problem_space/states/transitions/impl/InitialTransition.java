package com.tregouet.occam.data.problem_space.states.transitions.impl;

import java.util.ArrayList;
import java.util.Arrays;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.impl.Everything;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.impl.WhatIsThere;
import com.tregouet.occam.data.problem_space.states.productions.impl.ContextualizedOmegaProd;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.TransitionType;
import com.tregouet.occam.data.problem_space.states.transitions.dimensions.Nothing;
import com.tregouet.occam.data.problem_space.states.transitions.dimensions.This;

public class InitialTransition extends ConceptTransition implements IConceptTransition {

	public InitialTransition(Everything everything) {
		super(new ConceptTransitionIC(WhatIsThere.INSTANCE.iD(), ContextualizedOmegaProd.INSTANCE,
				Nothing.INSTANCE),
				new ConceptTransitionOIC(everything.iD(),
						new ArrayList<>(Arrays.asList(new AVariable[] { Nothing.INSTANCE, This.INSTANCE }))));
	}

	@Override
	public TransitionType type() {
		return TransitionType.INITIAL;
	}

}
