package com.tregouet.occam.alg.calculators.costs.functions;

import java.util.Comparator;

import com.tregouet.occam.alg.calculators.costs.functions.impl.ScoreThenCostThenRef;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;

public interface IFunctionComparator extends Comparator<ITransitionFunction> {
	
	default IFunctionComparator getInstance() {
		return ScoreThenCostThenRef.INSTANCE;
	}

}
