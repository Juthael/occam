package com.tregouet.occam.alg.scoring.costs.definitions;

import com.tregouet.occam.alg.scoring.costs.ICoster;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.descriptions.IGenusDifferentiaDefinition;

public interface IDefinitionCoster extends ICoster<IDefinitionCoster, IGenusDifferentiaDefinition> {
	
	default void setCosterParameters(ITransitionFunction transitionFunction) {
		//no parameter to be set;
	}

}
