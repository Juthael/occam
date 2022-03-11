package com.tregouet.occam.data.concepts.transitions.impl;

import java.util.ArrayList;
import java.util.Arrays;

import com.tregouet.occam.data.alphabets.productions.impl.ContextualizedEpsilon;
import com.tregouet.occam.data.concepts.impl.OntologicalCommitment;
import com.tregouet.occam.data.concepts.impl.StartState;
import com.tregouet.occam.data.concepts.transitions.IConceptTransition;
import com.tregouet.occam.data.concepts.transitions.dimensions.Nothing;
import com.tregouet.occam.data.concepts.transitions.dimensions.This;
import com.tregouet.occam.data.languages.generic.AVariable;

public class InitialTransition extends ConceptTransition implements IConceptTransition {

	public InitialTransition(OntologicalCommitment commitment) {
		super(
				new ConceptTransitionIC(
						StartState.INSTANCE, 
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
