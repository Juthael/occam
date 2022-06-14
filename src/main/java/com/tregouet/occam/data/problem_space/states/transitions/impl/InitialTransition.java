package com.tregouet.occam.data.problem_space.states.transitions.impl;

import java.util.Arrays;

import com.tregouet.occam.data.logical_structures.lambda_terms.IBindings;
import com.tregouet.occam.data.logical_structures.lambda_terms.impl.Bindings;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.impl.Everything;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.impl.WhatIsThere;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.impl.OmegaComputation;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.TransitionType;
import com.tregouet.occam.data.problem_space.states.transitions.impl.stack_default.NothingBinding;
import com.tregouet.occam.data.problem_space.states.transitions.impl.stack_default.vars.This;

public class InitialTransition extends ConceptTransition implements IConceptTransition {

	public InitialTransition(Everything everything) {
		super(
				new ConceptTransitionIC(
						WhatIsThere.INSTANCE.iD(),
						OmegaComputation.INSTANCE,
						NothingBinding.INSTANCE),
				new ConceptTransitionOIC(
						everything.iD(),
						Arrays.asList(
								new IBindings[] { 
										NothingBinding.INSTANCE, 
										new Bindings(Arrays.asList(new AVariable[] {This.INSTANCE}))} ))
				);
	}

	@Override
	public TransitionType type() {
		return TransitionType.INITIAL;
	}

}
