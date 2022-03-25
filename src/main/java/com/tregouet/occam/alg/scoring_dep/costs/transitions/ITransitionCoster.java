package com.tregouet.occam.alg.scoring_dep.costs.transitions;

import com.tregouet.occam.alg.scoring_dep.costs.ICoster;
import com.tregouet.occam.data.logical_structures.automata.IAutomaton;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.ICostedTransition;

public interface ITransitionCoster extends ICoster<ITransitionCoster, ICostedTransition> {
	
	default void setCosterParameters(IAutomaton automaton) {
		//no parameter to be set;
	}

}
