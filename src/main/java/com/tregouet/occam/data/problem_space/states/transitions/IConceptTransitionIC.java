package com.tregouet.occam.data.problem_space.states.transitions;

import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.input_config.IPushdownAutomatonIC;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;

public interface IConceptTransitionIC extends IPushdownAutomatonIC<IContextualizedProduction, AVariable> {

}
