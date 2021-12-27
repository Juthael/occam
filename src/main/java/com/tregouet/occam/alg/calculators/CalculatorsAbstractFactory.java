package com.tregouet.occam.alg.calculators;

import com.tregouet.occam.alg.calculators.costs.definitions.DefinitionCostingStrategy;
import com.tregouet.occam.alg.calculators.costs.definitions.IDefinitionCoster;
import com.tregouet.occam.alg.calculators.costs.definitions.impl.DefinitionCosterFactory;
import com.tregouet.occam.alg.calculators.costs.functions.FunctionCostingStrategy;
import com.tregouet.occam.alg.calculators.costs.functions.IFunctionCoster;
import com.tregouet.occam.alg.calculators.costs.functions.impl.FunctionCosterFactory;
import com.tregouet.occam.alg.calculators.scores.functions.FunctionScoringStrategy;
import com.tregouet.occam.alg.calculators.scores.functions.IFunctionScorer;
import com.tregouet.occam.alg.calculators.scores.functions.impl.FunctionScorerFactory;
import com.tregouet.occam.alg.calculators.scores.similarity.ISimilarityScorer;
import com.tregouet.occam.alg.calculators.scores.similarity.SimilarityScoringStrategy;
import com.tregouet.occam.alg.calculators.scores.similarity.impl.SimilarityScorerFactory;

public class CalculatorsAbstractFactory {

	public static final CalculatorsAbstractFactory INSTANCE = new CalculatorsAbstractFactory();
	private DefinitionCostingStrategy definitionCostingStrategy = null;
	private SimilarityScoringStrategy similarityScoringStrategy = null;
	private FunctionCostingStrategy functionCostingStrategy = null;
	private FunctionScoringStrategy functionScoringStrategy = null;
	
	private CalculatorsAbstractFactory() {
	}
	
	public IDefinitionCoster getDefinitionCoster() {
		return DefinitionCosterFactory.INSTANCE.apply(definitionCostingStrategy);
	}
	
	public ISimilarityScorer getSimilarityRater() {
		return SimilarityScorerFactory.INSTANCE.apply(similarityScoringStrategy);
	}
	
	public IFunctionCoster getTransitionFunctionCoster() {
		return FunctionCosterFactory.INSTANCE.apply(functionCostingStrategy);
	}
	
	public IFunctionScorer getTransitionFunctionScorer() {
		return FunctionScorerFactory.INSTANCE.apply(functionScoringStrategy);
	}
	
	public void setUpStrategy(ScoringStrategy overallStrategy) {
		switch (overallStrategy) {
		}
	}

}
