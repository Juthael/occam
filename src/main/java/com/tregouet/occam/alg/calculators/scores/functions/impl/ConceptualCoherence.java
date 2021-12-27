package com.tregouet.occam.alg.calculators.scores.functions.impl;

import com.tregouet.occam.alg.calculators.scores.functions.IFunctionScorer;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;

public class ConceptualCoherence implements IFunctionScorer {

	private ITransitionFunction transitionFunction = null;
	
	public ConceptualCoherence() {
	}

	@Override
	public IFunctionScorer input(ITransitionFunction transitionFunction) {
		this.transitionFunction = transitionFunction;
		return this;
	}

	@Override
	public void setScore() {
		transitionFunction.setScore(transitionFunction.getSimilarityCalculator().getCoherenceScore());
	}

}
