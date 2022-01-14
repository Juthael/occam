package com.tregouet.occam.alg.scoring.costs.transitions;

import com.tregouet.occam.alg.scoring.costs.ICoster;
import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.abstract_machines.transition_rules.ICostedTransition;

public interface ITransitionCoster extends ICoster<ITransitionCoster, ICostedTransition> {
	
	default void setCosterParameters(IAutomaton automaton) {
		//no parameter to be set;
	}

}
