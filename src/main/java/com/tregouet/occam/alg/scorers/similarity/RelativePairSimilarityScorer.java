package com.tregouet.occam.alg.scorers.similarity;

import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;

public interface RelativePairSimilarityScorer extends RelativeSimilarityScorer {

	double score(Integer conceptID1, Integer conceptID2);

	@Override
	RelativePairSimilarityScorer setAsContext(Tree<Integer, ADifferentiae> classificationTree);

}
