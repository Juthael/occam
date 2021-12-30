package com.tregouet.occam.alg.scoring.scores.functions.impl;

import com.tregouet.occam.alg.scoring.scores.functions.IFunctionScorer;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;

public class ConceptualCoherence implements IFunctionScorer {

	public static final ConceptualCoherence INSTANCE = new ConceptualCoherence();
	private ITransitionFunction transitionFunction = null;
	
	private ConceptualCoherence() {
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