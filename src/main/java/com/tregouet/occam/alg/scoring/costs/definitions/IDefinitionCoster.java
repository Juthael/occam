package com.tregouet.occam.alg.scoring.costs.definitions;

import com.tregouet.occam.alg.scoring.costs.ICoster;
import com.tregouet.occam.data.automata.machines.IAutomaton;
import com.tregouet.occam.data.automata.machines.descriptions.IGenusDifferentiaDefinition;

public interface IDefinitionCoster extends ICoster<IDefinitionCoster, IGenusDifferentiaDefinition> {
	
	default void setCosterParameters(IAutomaton automaton) {
		//no parameter to be set;
	}

}
