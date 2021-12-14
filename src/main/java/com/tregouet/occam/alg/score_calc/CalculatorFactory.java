package com.tregouet.occam.alg.score_calc;

import com.tregouet.occam.alg.score_calc.concept_derivation_cost.ConceptDerivationCostStrategy;
import com.tregouet.occam.alg.score_calc.concept_derivation_cost.IConceptDerivationCostCalculator;
import com.tregouet.occam.alg.score_calc.concept_derivation_cost.impl.DerivationCostCalculatorFactory;
import com.tregouet.occam.alg.score_calc.similarity_calc.ISimilarityCalculator;
import com.tregouet.occam.alg.score_calc.similarity_calc.SimilarityCalculationStrategy;
import com.tregouet.occam.alg.score_calc.similarity_calc.impl.SimilarityCalculatorFactory;
import com.tregouet.occam.alg.score_calc.transition_function_cost.ITransitionFunctionCostCalculator;
import com.tregouet.occam.alg.score_calc.transition_function_cost.TransitionFunctionCostStrategy;
import com.tregouet.occam.alg.score_calc.transition_function_cost.impl.TransitionFunctionCostCalcFactory;
import com.tregouet.occam.data.concepts.IIsA;

public class CalculatorFactory {

	public static final CalculatorFactory INSTANCE = new CalculatorFactory();
	private ConceptDerivationCostStrategy derivationCostStrategy = null;
	private SimilarityCalculationStrategy similarityStrategy = null;
	private TransitionFunctionCostStrategy transitionFunctionCostStrategy = null;
	
	private CalculatorFactory() {
	}
	
	public IConceptDerivationCostCalculator getConceptDerivationCostCalculator() {
		return DerivationCostCalculatorFactory.INSTANCE.apply(derivationCostStrategy);
	}
	
	public ISimilarityCalculator getSimilarityCalculator() {
		return SimilarityCalculatorFactory.INSTANCE.apply(similarityStrategy);
	}
	
	public ITransitionFunctionCostCalculator getTransitionFunctionCostCalculator() {
		return TransitionFunctionCostCalcFactory.INSTANCE.apply(transitionFunctionCostStrategy);
	}
	
	public void setUpStrategy(OverallScoringStrategy overallStrategy) {
		switch (overallStrategy) {
			case CONCEPTUAL_COHERENCE : 
				derivationCostStrategy = ConceptDerivationCostStrategy.ENTROPY_REDUCTION;
				similarityStrategy = SimilarityCalculationStrategy.RATIO_MODEL;
				transitionFunctionCostStrategy = TransitionFunctionCostStrategy.NUMBER_OF_TRANSITIONS;
				break;
			case CONCEPTUAL_COHERENCE_MULTIFRAME : 
				derivationCostStrategy = ConceptDerivationCostStrategy.ENTROPY_REDUCTION_MULTIFRAME;
				similarityStrategy = SimilarityCalculationStrategy.RATIO_MODEL;
				transitionFunctionCostStrategy = TransitionFunctionCostStrategy.NUMBER_OF_TRANSITIONS;
				break;
		}
	}

}
