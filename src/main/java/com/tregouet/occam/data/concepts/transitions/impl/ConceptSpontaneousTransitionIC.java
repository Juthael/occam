package com.tregouet.occam.data.concepts.transitions.impl;

import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.alphabets.productions.impl.ContextualizedEpsilon;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.concepts.transitions.dimensions.This;
import com.tregouet.occam.data.languages.generic.AVariable;

public class ConceptSpontaneousTransitionIC extends ConceptTransitionIC
		implements IConceptTransitionIC {

	public ConceptSpontaneousTransitionIC(IConcept inputState, IContextualizedProduction inputSymbol,
			AVariable stackSymbol) {
		super(inputState, new ContextualizedEpsilon(null, null), This.INSTANCE);
	}

}
