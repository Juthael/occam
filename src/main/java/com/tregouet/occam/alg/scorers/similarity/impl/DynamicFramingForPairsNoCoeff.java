package com.tregouet.occam.alg.scorers.similarity.impl;

import com.tregouet.occam.alg.scorers.similarity.RelativePairSimilarityScorer;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.Functions;

public class DynamicFramingForPairsNoCoeff extends RelativeAbstractSimilarityScorer
		implements RelativePairSimilarityScorer {

	@Override
	public double score(Integer conceptID1, Integer conceptID2) {
		Integer genus = Functions.commonAncestor(classificationTree, conceptID1, conceptID2);
		return getCoeffFreeDefinitionCostOf(genus);
	}

	@Override
	public RelativePairSimilarityScorer setAsContext(Tree<Integer, ADifferentiae> classificationTree) {
		this.classificationTree = classificationTree;
		return this;
	}

}
