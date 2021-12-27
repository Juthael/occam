package com.tregouet.occam.alg.calculators.scores.impl;

import com.tregouet.occam.alg.calculators.scores.ISimilarityScorer;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;

public class DynamicFramingSimCalculator extends AbstractSimCalculator implements ISimilarityScorer {

	public DynamicFramingSimCalculator() {
		// TODO Auto-generated constructor stub
	}

	public DynamicFramingSimCalculator(ITransitionFunction transitionFunction) {
		super(transitionFunction);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double howSimilar(int conceptID1, int conceptID2) {
		// TODO
	}

	@Override
	public double howSimilarTo(int conceptID1, int conceptID2) {
		// TODO 
	}

}
