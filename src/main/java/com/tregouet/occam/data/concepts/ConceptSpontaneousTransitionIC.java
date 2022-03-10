package com.tregouet.occam.data.concepts;

import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.alphabets.productions.impl.ContextualizedEpsilon;
import com.tregouet.occam.data.automata.transitions.input_config.IPushdownAutomatonIC;
import com.tregouet.occam.data.concepts.transitions.ConceptTransitionIC;
import com.tregouet.occam.data.concepts.transitions.dimensions.OntologicalCommitment;
import com.tregouet.occam.data.languages.generic.AVariable;

public class ConceptSpontaneousTransitionIC extends ConceptTransitionIC
		implements IPushdownAutomatonIC<IContextualizedProduction, AVariable> {

	public ConceptSpontaneousTransitionIC(IConcept inputState, IContextualizedProduction inputSymbol,
			AVariable stackSymbol) {
		super(inputState, new ContextualizedEpsilon(null, null), OntologicalCommitment.INSTANCE);
	}

}
