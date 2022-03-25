package com.tregouet.occam.data.representations.properties.transitions.impl;

import java.util.ArrayList;
import java.util.Arrays;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.alphabets.productions.impl.ContextualizedEpsilonProd;
import com.tregouet.occam.data.concepts.impl.Everything;
import com.tregouet.occam.data.representations.concepts.impl.WhatIsThere;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.properties.transitions.TransitionType;
import com.tregouet.occam.data.representations.properties.transitions.dimensions.Nothing;
import com.tregouet.occam.data.representations.properties.transitions.dimensions.This;

public class InitialTransition extends ConceptTransition implements IConceptTransition {

	public InitialTransition(Everything everything) {
		super(
				new ConceptTransitionIC(
						WhatIsThere.INSTANCE.getID(), 
						new ContextualizedEpsilonProd(null, null), 
						Nothing.INSTANCE),
				new ConceptTransitionOIC(
						everything.getID(), 
						new ArrayList<AVariable>(
								Arrays.asList(
										new AVariable[] {Nothing.INSTANCE, This.INSTANCE})))
		);
	}

	@Override
	public TransitionType type() {
		return TransitionType.INITIAL;
	}
	
}
