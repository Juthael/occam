package com.tregouet.occam.data.structures.representations.transitions.impl;

import java.util.Arrays;

import com.tregouet.occam.data.structures.lambda_terms.IBindings;
import com.tregouet.occam.data.structures.lambda_terms.impl.Bindings;
import com.tregouet.occam.data.structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.structures.representations.classifications.concepts.impl.Everything;
import com.tregouet.occam.data.structures.representations.classifications.concepts.impl.WhatIsThere;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.abstr_app.impl.OmegaOperator;
import com.tregouet.occam.data.structures.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.structures.representations.transitions.TransitionType;
import com.tregouet.occam.data.structures.representations.transitions.impl.stack_default.NothingBinding;
import com.tregouet.occam.data.structures.representations.transitions.impl.stack_default.vars.This;

public class InitialTransition extends ConceptTransition implements IConceptTransition {

	public InitialTransition(Everything everything) {
		super(
				new ConceptTransitionIC(
						WhatIsThere.INSTANCE.iD(),
						OmegaOperator.INSTANCE,
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
