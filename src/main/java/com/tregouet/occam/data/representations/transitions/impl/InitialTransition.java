package com.tregouet.occam.data.representations.transitions.impl;

import java.util.ArrayList;
import java.util.Arrays;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.representations.concepts.impl.Everything;
import com.tregouet.occam.data.representations.concepts.impl.WhatIsThere;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.TransitionType;
import com.tregouet.occam.data.representations.transitions.dimensions.Nothing;
import com.tregouet.occam.data.representations.transitions.dimensions.This;
import com.tregouet.occam.data.representations.transitions.productions.impl.ContextualizedEpsilonProd;

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
