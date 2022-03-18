package com.tregouet.occam.data.representations.transitions;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.automata.transition_functions.transitions.input_config.IPushdownAutomatonIC;

public interface IConceptTransitionIC extends IPushdownAutomatonIC<IContextualizedProduction, AVariable> {

}
