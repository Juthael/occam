package com.tregouet.occam.alg.scorers.similarity;

import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.descriptions.metrics.subsets.IConceptSubsetIDs;
import com.tregouet.tree_finder.data.Tree;

public interface SubsetSimilarityScorer extends SimilarityScorer<IConceptSubsetIDs> {

	@Override
	SubsetSimilarityScorer setAsContext(Tree<Integer, ADifferentiae> classificationTree);

}
