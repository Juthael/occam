package com.tregouet.occam.alg.scorers.similarity.impl;

import com.tregouet.occam.alg.scorers.Scorer;
import com.tregouet.occam.alg.scorers.similarity.PairSimilarityScorer;
import com.tregouet.occam.data.logical_structures.orders.total.impl.DoubleScore;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.descriptions.metrics.subsets.IConceptPairIDs;
import com.tregouet.tree_finder.utils.Functions;

public class DynamicFramingForPairs extends AbstractSimilarityScorer<IConceptPairIDs> implements PairSimilarityScorer {

	@Override
	public Scorer<IConceptPairIDs, DoubleScore> setAsContext(IDescription description) {
		classificationTree = description.asGraph();
		return this;
	}

	@Override
	public DoubleScore apply(IConceptPairIDs pair) {
		Integer genus = Functions.commonAncestor(classificationTree, pair.first(), pair.second());
		return new DoubleScore(getDefinitionCostOf(genus));
	}

}
