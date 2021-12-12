package com.tregouet.occam.alg.score_calc.similarity_calc.impl;

import java.util.function.Function;

import com.tregouet.occam.alg.score_calc.similarity_calc.ISimilarityCalculator;
import com.tregouet.occam.alg.score_calc.similarity_calc.SimilarityCalculationStrategy;

public class SimilarityCalculatorFactory implements Function<SimilarityCalculationStrategy, ISimilarityCalculator> {
	
	public static final SimilarityCalculatorFactory INSTANCE = new SimilarityCalculatorFactory();
	
	private SimilarityCalculatorFactory() {
	}

	@Override
	public ISimilarityCalculator apply(SimilarityCalculationStrategy strategy) {
		switch (strategy) {
			case CONTRAST_MODEL :
				return new ContrastModel();
			case RATIO_MODEL : 
				return new RatioModel();
			default : 
				return null;
		}
	}

}
