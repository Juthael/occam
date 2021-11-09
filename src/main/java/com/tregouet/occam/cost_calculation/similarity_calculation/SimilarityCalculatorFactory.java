package com.tregouet.occam.cost_calculation.similarity_calculation;

import java.util.function.Function;

import com.tregouet.occam.cost_calculation.SimilarityCalculationStrategy;
import com.tregouet.occam.cost_calculation.similarity_calculation.impl.ContrastModel;

public class SimilarityCalculatorFactory implements Function<SimilarityCalculationStrategy, ISimilarityCalculator> {

	public SimilarityCalculatorFactory() {
	}

	@Override
	public ISimilarityCalculator apply(SimilarityCalculationStrategy strategy) {
		switch (strategy) {
			case CONTRAST_MODEL :
				return new ContrastModel();
			default : 
				return null;
		}
	}

}
