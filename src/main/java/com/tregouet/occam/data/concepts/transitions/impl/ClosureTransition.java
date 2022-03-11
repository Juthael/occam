package com.tregouet.occam.data.concepts.transitions.impl;

import java.util.Arrays;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.transitions.IConceptTransition;
import com.tregouet.occam.data.concepts.transitions.dimensions.Nothing;
import com.tregouet.occam.data.languages.generic.AVariable;

public class ClosureTransition extends ConceptTransition implements IConceptTransition {

	public ClosureTransition(ConceptTransitionIC inputConfig, IConcept outputState) {
		super(inputConfig, new ConceptTransitionOIC(outputState, Arrays.asList(new AVariable[] {Nothing.INSTANCE})));
	}

}
