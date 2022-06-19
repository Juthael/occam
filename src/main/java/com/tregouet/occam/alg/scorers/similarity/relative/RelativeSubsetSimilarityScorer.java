package com.tregouet.occam.alg.scorers.similarity.relative;

import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.descriptions.metrics.subsets.IConceptSubsetIDs;
import com.tregouet.tree_finder.data.Tree;

public interface RelativeSubsetSimilarityScorer extends RelativeSimilarityScorer<IConceptSubsetIDs> {

	@Override
	RelativeSubsetSimilarityScorer setAsContext(Tree<Integer, ADifferentiae> classificationTree);

}
