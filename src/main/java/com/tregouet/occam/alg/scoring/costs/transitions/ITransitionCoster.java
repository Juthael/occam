package com.tregouet.occam.alg.scoring.costs.transitions;

import com.tregouet.occam.alg.scoring.costs.ICoster;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.transitions.ICostedTransition;
import com.tregouet.occam.data.abstract_machines.transitions.ITransition;

public interface ITransitionCoster extends ICoster<ITransitionCoster, ICostedTransition> {
	
	void setNewCosterParameters(ITransitionFunction transitionFunction);

}
