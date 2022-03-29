package com.tregouet.occam.alg.scorers.similarity.impl;

import com.tregouet.occam.alg.scorers.similarity.SubsetSimilarityScorer;
import com.tregouet.occam.data.logical_structures.scores.impl.DoubleScore;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.descriptions.subsets.IConceptSubsetIDs;
import com.tregouet.tree_finder.utils.Functions;

public class DynamicFramingForSubsets extends AbstractSimilarityScorer<IConceptSubsetIDs> implements SubsetSimilarityScorer {
	
	public DynamicFramingForSubsets() {
	}

	@Override
	public DoubleScore apply(IConceptSubsetIDs conceptSubsetIds) {
		Integer genus = Functions.commonAncestor(classificationTree, conceptSubsetIds.getSubsetIDs());
		return new DoubleScore(getDefinitionCostOf(genus));
	}

	@Override
	public SubsetSimilarityScorer setAsContext(IDescription description) {
		classificationTree = description.asGraph();
		return this;
	}

}
