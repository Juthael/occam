package com.tregouet.occam.alg.scorers.similarity.relative.impl;

import com.tregouet.occam.alg.scorers.similarity.relative.RelativePairSimilarityScorer;
import com.tregouet.occam.data.logical_structures.scores.impl.DoubleScore;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.descriptions.metrics.subsets.IConceptPairIDs;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.Functions;

public class DynamicFramingForPairs extends RelativeAbstractSimilarityScorer<IConceptPairIDs> implements RelativePairSimilarityScorer {

	@Override
	public DoubleScore apply(IConceptPairIDs pair) {
		Integer genus = Functions.commonAncestor(classificationTree, pair.first(), pair.second());
		return new DoubleScore(getDefinitionCostOf(genus));
	}

	@Override
	public DynamicFramingForPairs setAsContext(Tree<Integer, ADifferentiae> classificationTree) {
		this.classificationTree = classificationTree;
		return this;
	}

}
