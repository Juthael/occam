package com.tregouet.occam.cost_calculation.similarity_calculation;

import com.tregouet.occam.cost_calculation.SimilarityCalculationStrategy;
import com.tregouet.occam.cost_calculation.similarity_calculation.impl.ContrastModel;

public class SimilarityCalculatorFactory {

	private SimilarityCalculatorFactory() {
	}

	public static ISimilarityCalculator apply(SimilarityCalculationStrategy strategy) {
		switch (strategy) {
			case CONTRAST_MODEL :
				return new ContrastModel();
			default : 
				return null;
		}
	}

}
