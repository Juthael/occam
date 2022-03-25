package com.tregouet.occam.alg.scoring_dep.costs.definitions;

import com.tregouet.occam.alg.scoring_dep.costs.ICoster;
import com.tregouet.occam.data.logical_structures.automata.IAutomaton;
import com.tregouet.occam.data.logical_structures.automata.machines.deprec.IGenusDifferentiaDefinition_dep;

public interface IDefinitionCoster extends ICoster<IDefinitionCoster, IGenusDifferentiaDefinition_dep> {
	
	default void setCosterParameters(IAutomaton automaton) {
		//no parameter to be set;
	}

}
