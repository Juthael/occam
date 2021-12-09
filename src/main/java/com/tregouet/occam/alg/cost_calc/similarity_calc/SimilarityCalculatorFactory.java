package com.tregouet.occam.alg.cost_calc.similarity_calc;

import com.tregouet.occam.alg.cost_calc.SimilarityCalculationStrategy;
import com.tregouet.occam.alg.cost_calc.similarity_calc.impl.ContrastModel;
import com.tregouet.occam.alg.cost_calc.similarity_calc.impl.RatioModel;

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
