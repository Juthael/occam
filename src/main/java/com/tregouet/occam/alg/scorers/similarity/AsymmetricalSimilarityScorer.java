package com.tregouet.occam.alg.scorers.similarity;

import com.tregouet.occam.data.problem_space.states.descriptions.metrics.subsets.IConceptPairIDs;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;

public interface AsymmetricalSimilarityScorer extends SimilarityScorer<IConceptPairIDs> {
	
	AsymmetricalSimilarityScorer setAsContext(Tree<Integer, ADifferentiae> classificationTree);

}
