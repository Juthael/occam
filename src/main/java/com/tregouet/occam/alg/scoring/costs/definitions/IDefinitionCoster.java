package com.tregouet.occam.alg.scoring.costs.definitions;

import com.tregouet.occam.alg.scoring.costs.ICoster;
import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.abstract_machines.automatons.descriptions.IGenusDifferentiaDefinition;

public interface IDefinitionCoster extends ICoster<IDefinitionCoster, IGenusDifferentiaDefinition> {
	
	default void setCosterParameters(IAutomaton automaton) {
		//no parameter to be set;
	}

}
