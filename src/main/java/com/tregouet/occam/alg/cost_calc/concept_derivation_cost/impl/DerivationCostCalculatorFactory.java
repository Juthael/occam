package com.tregouet.occam.alg.cost_calc.concept_derivation_cost.impl;

import com.tregouet.occam.alg.cost_calc.concept_derivation_cost.ConceptDerivationCostCalcStrategy;
import com.tregouet.occam.alg.cost_calc.concept_derivation_cost.IConceptDerivationCostCalculator;

public class DerivationCostCalculatorFactory {

	private DerivationCostCalculatorFactory() {
	}
	
	public static IConceptDerivationCostCalculator apply(ConceptDerivationCostCalcStrategy strategy) {
		switch (strategy) {
			case ENTROPY_REDUCTION : 
				return new EntropyReductionCalculator();
			default : 
				return null;
		}
	}

}
