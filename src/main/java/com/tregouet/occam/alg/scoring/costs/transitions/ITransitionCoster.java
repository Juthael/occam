package com.tregouet.occam.alg.scoring.costs.transitions;

import com.tregouet.occam.alg.scoring.costs.ICoster;
import com.tregouet.occam.data.automata.machines.IAutomaton;
import com.tregouet.occam.data.automata.transition_rules.ICostedTransition;

public interface ITransitionCoster extends ICoster<ITransitionCoster, ICostedTransition> {
	
	default void setCosterParameters(IAutomaton automaton) {
		//no parameter to be set;
	}

}
