package com.tregouet.occam.alg.scorers.similarity;

import com.tregouet.occam.data.representations.descriptions.metrics.subsets.IConceptPairIDs;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

public interface PairSimilarityScorer extends SimilarityScorer<IConceptPairIDs> {
	
	PairSimilarityScorer setAsContext(Tree<Integer, AbstractDifferentiae> classificationTree);

}
