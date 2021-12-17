package com.tregouet.occam.alg.calculators.costs.dep.concept_derivation.impl;

import java.util.function.Function;

import com.tregouet.occam.alg.calculators.costs.dep.concept_derivation.ConceptDerivationCostStrategy;
import com.tregouet.occam.alg.calculators.costs.dep.concept_derivation.IConceptDerivationCostCalculator;

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
			case ENTROPY_REDUCTION_MULTIFRAME : 
				return new EntropyReductionMultiFrame();
			default : 
				return null;
		}
	}

}
