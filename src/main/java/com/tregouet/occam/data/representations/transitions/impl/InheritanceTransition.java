package com.tregouet.occam.data.representations.transitions.impl;

import java.util.Arrays;

import com.tregouet.occam.data.alphabets.productions.impl.ContextualizedEpsilon;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.representations.IConcept;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.dimensions.Nothing;

public class InheritanceTransition extends ConceptTransition implements IConceptTransition {

	public InheritanceTransition(IConcept inputState, IConcept outputState) {
		super(new ConceptTransitionIC(inputState, new ContextualizedEpsilon(null, null), Nothing.INSTANCE), 
				new ConceptTransitionOIC(outputState, Arrays.asList(new AVariable[] {Nothing.INSTANCE})));
	}

}