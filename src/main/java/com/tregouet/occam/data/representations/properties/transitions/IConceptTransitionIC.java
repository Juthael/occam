package com.tregouet.occam.data.representations.properties.transitions;

import com.tregouet.occam.data.languages.alphabets.domain_specific.IContextualizedProduction;
import com.tregouet.occam.data.languages.alphabets.generic.AVariable;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.input_config.IPushdownAutomatonIC;

public interface IConceptTransitionIC extends IPushdownAutomatonIC<IContextualizedProduction, AVariable> {

}
