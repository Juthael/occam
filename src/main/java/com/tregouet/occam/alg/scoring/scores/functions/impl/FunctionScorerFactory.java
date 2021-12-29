package com.tregouet.occam.alg.scoring.scores.functions.impl;

import java.util.function.Function;

import com.tregouet.occam.alg.scoring.scores.functions.FunctionScoringStrategy;
import com.tregouet.occam.alg.scoring.scores.functions.IFunctionScorer;

public class FunctionScorerFactory implements
		Function<FunctionScoringStrategy, IFunctionScorer> {

	public static final FunctionScorerFactory INSTANCE = new FunctionScorerFactory();
	
	private FunctionScorerFactory() {
	}

	@Override
	public IFunctionScorer apply(FunctionScoringStrategy strategy) {
		switch (strategy) {
			case CONCEPTUAL_COHERENCE : 
				return ConceptualCoherence.INSTANCE;
			default : 
				return null;
		}
	}

}
