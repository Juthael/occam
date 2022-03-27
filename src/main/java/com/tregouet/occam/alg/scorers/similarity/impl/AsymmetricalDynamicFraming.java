package com.tregouet.occam.alg.scorers.similarity.impl;

import com.tregouet.occam.alg.scorers.scores.DoubleScore;
import com.tregouet.occam.alg.scorers.similarity.AsymmetricalSimilarityScorer;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.descriptions.subsets.IConceptAsymmetricalPairIDs;
import com.tregouet.tree_finder.utils.Functions;

public class AsymmetricalDynamicFraming extends AbstractSimilarityScorer<IConceptAsymmetricalPairIDs> 
	implements AsymmetricalSimilarityScorer {
	
	public AsymmetricalDynamicFraming() {
	}

	@Override
	public DoubleScore apply(IConceptAsymmetricalPairIDs iDPair) {
		Double genusDefinitionCost;
		Double targetDefinitionCost;
		Integer genusID = Functions.commonAncestor(classificationTree, iDPair.getSourceID(), iDPair.getTargetID());
		genusDefinitionCost = getDefinitionCostOf(genusID);
		targetDefinitionCost = getDefinitionCostOf(iDPair.getTargetID());
		return new DoubleScore(genusDefinitionCost / targetDefinitionCost);
	}
	
	@Override
	public AsymmetricalSimilarityScorer setAsContext(IDescription description) {
		classificationTree = description.asGraph();
		return this;
	}	

}
