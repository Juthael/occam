package com.tregouet.occam.data.representations.transitions;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.automata.transitions.output_config.IPushdownAutomatonOIC;
import com.tregouet.occam.data.representations.IConcept;

public interface IConceptTransitionOIC extends IPushdownAutomatonOIC<AVariable> {
	
	@Override
	IConcept getOutputState();

}
