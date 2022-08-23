package com.tregouet.occam.alg.scorers.similarity_DEP.impl;

import com.tregouet.occam.alg.scorers.similarity_DEP.RelativePairSimilarityScorerDEP;
import com.tregouet.occam.data.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.Functions;

public class DynamicFramingForPairsDEP extends RelativeAbstractSimilarityScorerDEP implements RelativePairSimilarityScorerDEP {

	@Override
	public double score(Integer conceptID1, Integer conceptID2) {
		Integer genus = Functions.commonAncestor(classificationTree, conceptID1, conceptID2);
		return getDefinitionCostOf(genus);
	}

	@Override
	public DynamicFramingForPairsDEP setAsContext(Tree<Integer, ADifferentiae> classificationTree) {
		this.classificationTree = classificationTree;
		return this;
	}

}
