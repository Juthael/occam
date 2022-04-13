package com.tregouet.occam.alg.builders.representations.fact_evaluators;

import com.tregouet.occam.alg.builders.representations.fact_evaluators.impl.SalienceAwareFEBuilder;
import com.tregouet.occam.alg.builders.representations.fact_evaluators.impl.SalienceBlindFEBuilder;

public class FactEvaluatorBuilderFactory {
	
	public static final FactEvaluatorBuilderFactory INSTANCE = new FactEvaluatorBuilderFactory();
	
	private FactEvaluatorBuilderFactory() {
	}
	
	public FactEvaluatorBuilder apply(FactEvaluatorBuilderStrategy strategy) {
		switch (strategy) {
			case SALIENCE_AWARE : 
				return SalienceAwareFEBuilder.INSTANCE;
			case SALIENCE_BLIND : 
				return SalienceBlindFEBuilder.INSTANCE;
			default : 
				return null;
		}
	}

}
