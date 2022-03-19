package com.tregouet.occam.data.representations.properties.transitions.impl;

import java.util.Arrays;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.alphabets.productions.impl.ContextualizedEpsilon;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.properties.transitions.dimensions.This;

public class SpontaneousTransition extends ConceptTransition implements IConceptTransition {

	public SpontaneousTransition(IConcept inputState, IConcept outputState) {
		super(new ConceptTransitionIC(inputState.getID(), new ContextualizedEpsilon(null, null), This.INSTANCE), 
				new ConceptTransitionOIC(outputState.getID(), Arrays.asList(new AVariable[] {This.INSTANCE})));
	}

}
