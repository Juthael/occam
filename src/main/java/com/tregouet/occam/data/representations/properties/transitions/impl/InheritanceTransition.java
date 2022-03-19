package com.tregouet.occam.data.representations.properties.transitions.impl;

import java.util.Arrays;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.alphabets.productions.impl.ContextualizedEpsilon;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.properties.transitions.dimensions.Nothing;

public class InheritanceTransition extends ConceptTransition implements IConceptTransition {

	public InheritanceTransition(int inputStateID, int outputStateID) {
		super(new ConceptTransitionIC(inputStateID, new ContextualizedEpsilon(null, null), Nothing.INSTANCE), 
				new ConceptTransitionOIC(outputStateID, Arrays.asList(new AVariable[] {Nothing.INSTANCE})));
	}

}
