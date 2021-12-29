package com.tregouet.occam.alg.scoring;

import com.tregouet.occam.alg.scoring.costs.definitions.DefinitionCostingStrategy;
import com.tregouet.occam.alg.scoring.costs.definitions.IDefinitionCoster;
import com.tregouet.occam.alg.scoring.costs.definitions.impl.DefinitionCosterFactory;
import com.tregouet.occam.alg.scoring.costs.functions.FunctionCostingStrategy;
import com.tregouet.occam.alg.scoring.costs.functions.IFunctionCoster;
import com.tregouet.occam.alg.scoring.costs.functions.impl.FunctionCosterFactory;
import com.tregouet.occam.alg.scoring.costs.transitions.ITransitionCoster;
import com.tregouet.occam.alg.scoring.costs.transitions.TransitionCostingStrategy;
import com.tregouet.occam.alg.scoring.costs.transitions.impl.TransitionCosterFactory;
import com.tregouet.occam.alg.scoring.scores.functions.FunctionScoringStrategy;
import com.tregouet.occam.alg.scoring.scores.functions.IFunctionScorer;
import com.tregouet.occam.alg.scoring.scores.functions.impl.FunctionScorerFactory;
import com.tregouet.occam.alg.scoring.scores.similarity.ISimilarityScorer;
import com.tregouet.occam.alg.scoring.scores.similarity.SimilarityScoringStrategy;
import com.tregouet.occam.alg.scoring.scores.similarity.impl.SimilarityScorerFactory;

public class CalculatorsAbstractFactory {

	public static final CalculatorsAbstractFactory INSTANCE = new CalculatorsAbstractFactory();
	private TransitionCostingStrategy transitionCostingStrategy = null;
	private DefinitionCostingStrategy definitionCostingStrategy = null;
	private FunctionCostingStrategy functionCostingStrategy = null;
	private SimilarityScoringStrategy similarityScoringStrategy = null;
	private FunctionScoringStrategy functionScoringStrategy = null;
	
	private CalculatorsAbstractFactory() {
	}
	
	public ITransitionCoster getTransitionCoster() {
		return TransitionCosterFactory.INSTANCE.apply(transitionCostingStrategy);
	}
	
	public IDefinitionCoster getDefinitionCoster() {
		return DefinitionCosterFactory.INSTANCE.apply(definitionCostingStrategy);
	}
	
	public IFunctionCoster getTransitionFunctionCoster() {
		return FunctionCosterFactory.INSTANCE.apply(functionCostingStrategy);
	}
	
	public ISimilarityScorer getSimilarityRater() {
		return SimilarityScorerFactory.INSTANCE.apply(similarityScoringStrategy);
	}	
	
	public IFunctionScorer getTransitionFunctionScorer() {
		return FunctionScorerFactory.INSTANCE.apply(functionScoringStrategy);
	}
	
	public void setUpStrategy(ScoringStrategy overallStrategy) {
		switch (overallStrategy) {
			case SCORING_STRATEGY_1 : 
				transitionCostingStrategy = TransitionCostingStrategy.ENTROPY_REDUCTION;
				definitionCostingStrategy = DefinitionCostingStrategy.TRANSITION_COSTS;
				functionCostingStrategy = FunctionCostingStrategy.NB_OF_INSTANTIATIONS;
				similarityScoringStrategy = SimilarityScoringStrategy.DYNAMIC_FRAMING;
				functionScoringStrategy = FunctionScoringStrategy.CONCEPTUAL_COHERENCE;
			default : 
				break;
		}
	}

}
