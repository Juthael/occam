package com.tregouet.occam.alg.scorers.similarity;

import java.util.Set;

import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;

public interface RelativeSubsetSimilarityScorer extends RelativeSimilarityScorer {

	double score(Set<Integer> conceptIDs);

	@Override
	RelativeSubsetSimilarityScorer setAsContext(Tree<Integer, ADifferentiae> classificationTree);

}
