package com.tregouet.occam.alg.scorers.similarity.relative;

import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.descriptions.metrics.subsets.IConceptPairIDs;
import com.tregouet.tree_finder.data.Tree;

public interface RelativeAsymmetricalSimilarityScorer extends RelativeSimilarityScorer<IConceptPairIDs> {

	@Override
	RelativeAsymmetricalSimilarityScorer setAsContext(Tree<Integer, ADifferentiae> classificationTree);

}
