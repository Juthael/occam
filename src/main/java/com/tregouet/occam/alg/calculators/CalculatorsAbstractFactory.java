package com.tregouet.occam.alg.calculators;

import java.util.Comparator;

import com.tregouet.occam.alg.calculators.costs.dep.concept_derivation.ConceptDerivationCostStrategy;
import com.tregouet.occam.alg.calculators.costs.dep.concept_derivation.IConceptDerivationCostCalculator;
import com.tregouet.occam.alg.calculators.costs.dep.concept_derivation.impl.DerivationCostCalculatorFactory;
import com.tregouet.occam.alg.calculators.costs.functions.FunctionCostingStrategy;
import com.tregouet.occam.alg.calculators.costs.functions.IFunctionCoster;
import com.tregouet.occam.alg.calculators.costs.functions.impl.FunctionCosterFactory;
import com.tregouet.occam.alg.calculators.scores.ISimilarityScorer;
import com.tregouet.occam.alg.calculators.scores.SimilarityScoringStrategy;
import com.tregouet.occam.alg.calculators.scores.impl.SimilarityCalculatorFactory;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;

public class CalculatorsAbstractFactory {

	public static final CalculatorsAbstractFactory INSTANCE = new CalculatorsAbstractFactory();
	private ConceptDerivationCostStrategy derivationCostStrategy = null;
	private SimilarityScoringStrategy similarityScoringStrategy = null;
	private FunctionCostingStrategy functionCostingStrategy = null;
	private Comparator<ITransitionFunction> transFuncComparator = null;
	
	private CalculatorsAbstractFactory() {
	}
	
	public IConceptDerivationCostCalculator getConceptDerivationCostCalculator() {
		return DerivationCostCalculatorFactory.INSTANCE.apply(derivationCostStrategy);
	}
	
	public ISimilarityScorer getSimilarityCalculator() {
		return SimilarityCalculatorFactory.INSTANCE.apply(similarityScoringStrategy);
	}
	
	public IFunctionCoster getTransitionFunctionCostCalculator() {
		return FunctionCosterFactory.INSTANCE.apply(functionCostingStrategy);
	}
	
	public Comparator<ITransitionFunction> getTransFuncComparator() {
		return transFuncComparator;
	}
	
	public void setUpStrategy(ScoringStrategy overallStrategy) {
		switch (overallStrategy) {
		}
	}

}
