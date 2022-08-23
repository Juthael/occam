package com.tregouet.occam.alg.scorers.similarity_DEP;

import com.tregouet.occam.data.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;

public interface RelativeAsymmetricalSimilarityScorerDEP extends RelativeSimilarityScorerDEP {

	double score(Integer conceptID1, Integer conceptID2);

	@Override
	RelativeAsymmetricalSimilarityScorerDEP setAsContext(Tree<Integer, ADifferentiae> classificationTree);

}
