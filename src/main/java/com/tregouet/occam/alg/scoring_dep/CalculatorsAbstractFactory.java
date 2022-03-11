package com.tregouet.occam.alg.scoring_dep;

import com.tregouet.occam.alg.scoring_dep.costs.definitions.DefinitionCostingStrategy;
import com.tregouet.occam.alg.scoring_dep.costs.definitions.IDefinitionCoster;
import com.tregouet.occam.alg.scoring_dep.costs.definitions.impl.DefinitionCosterFactory;
import com.tregouet.occam.alg.scoring_dep.costs.functions.FunctionCostingStrategy;
import com.tregouet.occam.alg.scoring_dep.costs.functions.IFunctionCoster;
import com.tregouet.occam.alg.scoring_dep.costs.functions.impl.FunctionCosterFactory;
import com.tregouet.occam.alg.scoring_dep.costs.transitions.ITransitionCoster;
import com.tregouet.occam.alg.scoring_dep.costs.transitions.TransitionCostingStrategy;
import com.tregouet.occam.alg.scoring_dep.costs.transitions.impl.TransitionCosterFactory;
import com.tregouet.occam.alg.scoring_dep.scores.functions.FunctionScoringStrategy;
import com.tregouet.occam.alg.scoring_dep.scores.functions.IFunctionScorer;
import com.tregouet.occam.alg.scoring_dep.scores.functions.impl.FunctionScorerFactory;
import com.tregouet.occam.alg.scoring_dep.scores.similarity.ISimilarityScorer;
import com.tregouet.occam.alg.scoring_dep.scores.similarity.SimilarityScoringStrategy;
import com.tregouet.occam.alg.scoring_dep.scores.similarity.impl.SimilarityScorerFactory;

public class CalculatorsAbstractFactory {

	public static final CalculatorsAbstractFactory INSTANCE = new CalculatorsAbstractFactory();
	private TransitionCostingStrategy transitionCostingStrategy = null;
	private DefinitionCostingStrategy definitionCostingStrategy = null;
	private FunctionCostingStrategy functionCostingStrategy = null;
	private SimilarityScoringStrategy similarityScoringStrategy = null;
	private FunctionScoringStrategy functionScoringStrategy = null;
	
	private CalculatorsAbstractFactory() {
	}
	
	public IDefinitionCoster getDefinitionCoster() {
		return DefinitionCosterFactory.INSTANCE.apply(definitionCostingStrategy);
	}
	
	public ISimilarityScorer getSimilarityScorer() {
		return SimilarityScorerFactory.INSTANCE.apply(similarityScoringStrategy);
	}
	
	public ITransitionCoster getTransitionCoster() {
		return TransitionCosterFactory.INSTANCE.apply(transitionCostingStrategy);
	}
	
	public IFunctionCoster getTransitionFunctionCoster() {
		return FunctionCosterFactory.INSTANCE.apply(functionCostingStrategy);
	}	
	
	public IFunctionScorer getTransitionFunctionScorer() {
		return FunctionScorerFactory.INSTANCE.apply(functionScoringStrategy);
	}
	
	public void setUpStrategy(ScoringStrategy_dep overallStrategy) {
		switch (overallStrategy) {
			case SCORING_STRATEGY_1 : 
				transitionCostingStrategy = TransitionCostingStrategy.ENTROPY_REDUCTION;
				definitionCostingStrategy = DefinitionCostingStrategy.TRANSITION_COSTS;
				functionCostingStrategy = FunctionCostingStrategy.NB_OF_INSTANTIATED_VARIABLES;
				similarityScoringStrategy = SimilarityScoringStrategy.DYNAMIC_FRAMING;
				functionScoringStrategy = FunctionScoringStrategy.CONCEPTUAL_COHERENCE;
				break;
			case SCORING_STRATEGY_2 : 
				transitionCostingStrategy = TransitionCostingStrategy.COSTLESS;
				definitionCostingStrategy = DefinitionCostingStrategy.TRANSITIONS_ENTROPY_REDUCTION;
				functionCostingStrategy = FunctionCostingStrategy.NB_OF_INSTANTIATED_VARIABLES;
				similarityScoringStrategy = SimilarityScoringStrategy.DYNAMIC_FRAMING;
				functionScoringStrategy = FunctionScoringStrategy.CONCEPTUAL_COHERENCE;
				break;
			case SCORING_STRATEGY_3 : 
				transitionCostingStrategy = TransitionCostingStrategy.COSTLESS;
				definitionCostingStrategy = DefinitionCostingStrategy.PRODUCTIONS_ENTROPY_REDUCTION;
				functionCostingStrategy = FunctionCostingStrategy.NB_OF_INSTANTIATED_VARIABLES;
				similarityScoringStrategy = SimilarityScoringStrategy.DYNAMIC_FRAMING;
				functionScoringStrategy = FunctionScoringStrategy.CONCEPTUAL_COHERENCE;
			case SCORING_STRATEGY_4 : 
				transitionCostingStrategy = TransitionCostingStrategy.COSTLESS;
				definitionCostingStrategy = DefinitionCostingStrategy.INSTANTIATIONS_ENTROPY_REDUCTION;
				functionCostingStrategy = FunctionCostingStrategy.NB_OF_INSTANTIATED_VARIABLES;
				similarityScoringStrategy = SimilarityScoringStrategy.DYNAMIC_FRAMING;
				functionScoringStrategy = FunctionScoringStrategy.CONCEPTUAL_COHERENCE;
			case SCORING_STRATEGY_5 : 
				transitionCostingStrategy = TransitionCostingStrategy.COSTLESS;
				definitionCostingStrategy = DefinitionCostingStrategy.INSTANTIATIONS_ENTROPY_REDUCTION;
				functionCostingStrategy = FunctionCostingStrategy.NB_OF_BASIC_PRODUCTIONS;
				similarityScoringStrategy = SimilarityScoringStrategy.DYNAMIC_FRAMING;
				functionScoringStrategy = FunctionScoringStrategy.CONCEPTUAL_COHERENCE;
			case SCORING_STRATEGY_6 : 
				transitionCostingStrategy = TransitionCostingStrategy.COSTLESS;
				definitionCostingStrategy = DefinitionCostingStrategy.INSTANTIATIONS_ENTROPY_REDUCTION_OC;
				functionCostingStrategy = FunctionCostingStrategy.NB_OF_BASIC_PRODUCTIONS;
				similarityScoringStrategy = SimilarityScoringStrategy.DYNAMIC_FRAMING;
				functionScoringStrategy = FunctionScoringStrategy.CONCEPTUAL_COHERENCE;
			default : 
				break;
		}
	}

}
