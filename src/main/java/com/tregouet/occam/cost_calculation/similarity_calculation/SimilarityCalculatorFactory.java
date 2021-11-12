package com.tregouet.occam.cost_calculation.similarity_calculation;

import com.tregouet.occam.cost_calculation.SimilarityCalculationStrategy;
import com.tregouet.occam.cost_calculation.similarity_calculation.impl.ContrastModel;
import com.tregouet.occam.cost_calculation.similarity_calculation.impl.RatioModel;

public class SimilarityCalculatorFactory {

	private SimilarityCalculatorFactory() {
	}

	public static ISimilarityCalculator apply(SimilarityCalculationStrategy strategy) {
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
