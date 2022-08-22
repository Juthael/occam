package com.tregouet.occam.alg.scorers.similarity;

import com.tregouet.occam.data.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;

public interface RelativeSimilarityScorer {

	RelativeSimilarityScorer setAsContext(Tree<Integer, ADifferentiae> classificationTree);

}
