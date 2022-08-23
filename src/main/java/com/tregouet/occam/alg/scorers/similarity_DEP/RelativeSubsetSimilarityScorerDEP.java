package com.tregouet.occam.alg.scorers.similarity_DEP;

import java.util.Set;

import com.tregouet.occam.data.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;

public interface RelativeSubsetSimilarityScorerDEP extends RelativeSimilarityScorerDEP {

	double score(Set<Integer> conceptIDs);

	@Override
	RelativeSubsetSimilarityScorerDEP setAsContext(Tree<Integer, ADifferentiae> classificationTree);

}
