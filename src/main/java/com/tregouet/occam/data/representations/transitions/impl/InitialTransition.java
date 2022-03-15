package com.tregouet.occam.data.representations.transitions.impl;

import java.util.ArrayList;
import java.util.Arrays;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.alphabets.productions.impl.ContextualizedEpsilon;
import com.tregouet.occam.data.representations.impl.OntologicalCommitment;
import com.tregouet.occam.data.representations.impl.WhatIsThere;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.dimensions.Nothing;
import com.tregouet.occam.data.representations.transitions.dimensions.This;

public class InitialTransition extends ConceptTransition implements IConceptTransition {

	public InitialTransition(OntologicalCommitment commitment) {
		super(
				new ConceptTransitionIC(
						WhatIsThere.INSTANCE, 
						new ContextualizedEpsilon(null, null), 
						Nothing.INSTANCE),
				new ConceptTransitionOIC(
						commitment, 
						new ArrayList<AVariable>(
								Arrays.asList(
										new AVariable[] {Nothing.INSTANCE, This.INSTANCE})))
		);
	}
	
}
