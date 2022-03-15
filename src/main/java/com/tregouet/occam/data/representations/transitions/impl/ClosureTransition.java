package com.tregouet.occam.data.representations.transitions.impl;

import java.util.Arrays;

import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.representations.IConcept;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.dimensions.Nothing;

public class ClosureTransition extends ConceptTransition implements IConceptTransition {

	public ClosureTransition(ConceptTransitionIC inputConfig, IConcept outputState) {
		super(inputConfig, new ConceptTransitionOIC(outputState, Arrays.asList(new AVariable[] {Nothing.INSTANCE})));
	}

}
