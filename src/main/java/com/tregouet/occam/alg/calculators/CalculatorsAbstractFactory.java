package com.tregouet.occam.alg.calculators;

import java.util.Comparator;

import com.tregouet.occam.alg.calculators.costs.dep.concept_derivation.ConceptDerivationCostStrategy;
import com.tregouet.occam.alg.calculators.costs.dep.concept_derivation.IConceptDerivationCostCalculator;
import com.tregouet.occam.alg.calculators.costs.dep.concept_derivation.impl.DerivationCostCalculatorFactory;
import com.tregouet.occam.alg.calculators.costs.dep.transition_function.ITransitionFunctionCostCalculator;
import com.tregouet.occam.alg.calculators.costs.dep.transition_function.TransitionFunctionCostStrategy;
import com.tregouet.occam.alg.calculators.costs.dep.transition_function.impl.TransitionFunctionCostCalcFactory;
import com.tregouet.occam.alg.calculators.scores.ISimilarityScorer;
import com.tregouet.occam.alg.calculators.scores.SimilarityScoringStrategy;
import com.tregouet.occam.alg.calculators.scores.impl.SimilarityCalculatorFactory;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;

public class CalculatorsAbstractFactory {

	public static final CalculatorsAbstractFactory INSTANCE = new CalculatorsAbstractFactory();
	private ConceptDerivationCostStrategy derivationCostStrategy = null;
	private SimilarityScoringStrategy similarityScoringStrategy = null;
	private TransitionFunctionCostStrategy transitionFunctionCostStrategy = null;
	private Comparator<ITransitionFunction> transFuncComparator = null;
	
	private CalculatorsAbstractFactory() {
	}
	
	public IConceptDerivationCostCalculator getConceptDerivationCostCalculator() {
		return DerivationCostCalculatorFactory.INSTANCE.apply(derivationCostStrategy);
	}
	
	public ISimilarityScorer getSimilarityCalculator() {
		return SimilarityCalculatorFactory.INSTANCE.apply(similarityScoringStrategy);
	}
	
	public ITransitionFunctionCostCalculator getTransitionFunctionCostCalculator() {
		return TransitionFunctionCostCalcFactory.INSTANCE.apply(transitionFunctionCostStrategy);
	}
	
	public Comparator<ITransitionFunction> getTransFuncComparator() {
		return transFuncComparator;
	}
	
	public void setUpStrategy(ScoringStrategy overallStrategy) {
		switch (overallStrategy) {
		}
	}

}
