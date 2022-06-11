package com.tregouet.occam.alg.scorers.similarity;

import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.descriptions.metrics.subsets.IConceptPairIDs;
import com.tregouet.tree_finder.data.Tree;

public interface PairSimilarityScorer extends SimilarityScorer<IConceptPairIDs> {

	@Override
	PairSimilarityScorer setAsContext(Tree<Integer, ADifferentiae> classificationTree);

}
