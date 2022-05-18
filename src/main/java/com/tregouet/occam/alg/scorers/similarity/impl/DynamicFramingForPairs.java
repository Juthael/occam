package com.tregouet.occam.alg.scorers.similarity.impl;

import com.tregouet.occam.alg.scorers.similarity.PairSimilarityScorer;
import com.tregouet.occam.data.logical_structures.scores.impl.DoubleScore;
import com.tregouet.occam.data.problem_space.states.descriptions.metrics.subsets.IConceptPairIDs;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.Functions;

public class DynamicFramingForPairs extends AbstractSimilarityScorer<IConceptPairIDs> implements PairSimilarityScorer {

	@Override
	public DoubleScore apply(IConceptPairIDs pair) {
		Integer genus = Functions.commonAncestor(classificationTree, pair.first(), pair.second());
		return new DoubleScore(getDefinitionCostOf(genus));
	}

	@Override
	public DynamicFramingForPairs setAsContext(Tree<Integer, AbstractDifferentiae> classificationTree) {
		this.classificationTree = classificationTree;
		return this;
	}

}
