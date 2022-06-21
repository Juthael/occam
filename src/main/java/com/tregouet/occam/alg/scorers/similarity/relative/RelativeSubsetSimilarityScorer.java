package com.tregouet.occam.alg.scorers.similarity.relative;

import java.util.Set;

import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;

public interface RelativeSubsetSimilarityScorer extends RelativeSimilarityScorer {
	
	@Override
	RelativeSubsetSimilarityScorer setAsContext(Tree<Integer, ADifferentiae> classificationTree);

	double score(Set<Integer> conceptIDs);

}
