package com.tregouet.occam.alg.scorers.similarity.impl;

import com.tregouet.occam.alg.scorers.scores.DoubleScore;
import com.tregouet.occam.alg.scorers.similarity.BasicSimilarityScorer;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.descriptions.subsets.IConceptSubsetIDs;
import com.tregouet.tree_finder.utils.Functions;

public class DynamicFraming extends AbstractSimilarityScorer<IConceptSubsetIDs> implements BasicSimilarityScorer {
	
	public DynamicFraming() {
	}

	@Override
	public DoubleScore apply(IConceptSubsetIDs conceptSubsetIds) {
		Integer genus = Functions.commonAncestor(classificationTree, conceptSubsetIds.getSubsetIDs());
		return new DoubleScore(getDefinitionCostOf(genus));
	}

	@Override
	public BasicSimilarityScorer setAsContext(IDescription description) {
		classificationTree = description.asGraph();
		return this;
	}

}
