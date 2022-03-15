package com.tregouet.occam.data.representations.transitions.impl;

import java.util.Arrays;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.alphabets.productions.impl.ContextualizedEpsilon;
import com.tregouet.occam.data.representations.IConcept;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.dimensions.This;

public class SpontaneousTransition extends ConceptTransition implements IConceptTransition {

	public SpontaneousTransition(IConcept inputState, IConcept outputState) {
		super(new ConceptTransitionIC(inputState, new ContextualizedEpsilon(null, null), This.INSTANCE), 
				new ConceptTransitionOIC(outputState, Arrays.asList(new AVariable[] {This.INSTANCE})));
	}

}
