package com.tregouet.occam.data.representations.transitions;

import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.automata.transitions.input_config.IPushdownAutomatonIC;
import com.tregouet.occam.data.languages.generic.AVariable;

public interface IConceptTransitionIC extends IPushdownAutomatonIC<IContextualizedProduction, AVariable> {

}
