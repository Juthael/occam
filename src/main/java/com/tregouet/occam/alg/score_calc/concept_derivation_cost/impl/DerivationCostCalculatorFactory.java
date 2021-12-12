package com.tregouet.occam.alg.score_calc.concept_derivation_cost.impl;

import java.util.function.Function;

import com.tregouet.occam.alg.score_calc.concept_derivation_cost.ConceptDerivationCostStrategy;
import com.tregouet.occam.alg.score_calc.concept_derivation_cost.IConceptDerivationCostCalculator;

public class DerivationCostCalculatorFactory 
	implements Function<ConceptDerivationCostStrategy, IConceptDerivationCostCalculator> {
	
	public static final DerivationCostCalculatorFactory INSTANCE = new DerivationCostCalculatorFactory();
	
	private DerivationCostCalculatorFactory() {
	}
	
	@Override
	public IConceptDerivationCostCalculator apply(ConceptDerivationCostStrategy strategy) {
		switch (strategy) {
			case ENTROPY_REDUCTION : 
				return new EntropyReduction();
			default : 
				return null;
		}
	}

}
