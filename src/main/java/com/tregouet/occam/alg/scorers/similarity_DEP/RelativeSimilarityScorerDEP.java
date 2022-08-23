package com.tregouet.occam.alg.scorers.similarity_DEP;

import com.tregouet.occam.data.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;

public interface RelativeSimilarityScorerDEP {

	RelativeSimilarityScorerDEP setAsContext(Tree<Integer, ADifferentiae> classificationTree);

}
