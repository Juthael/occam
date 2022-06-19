package com.tregouet.occam.alg.scorers.similarity.relative.impl;

import com.tregouet.occam.alg.scorers.similarity.relative.RelativeSubsetSimilarityScorer;
import com.tregouet.occam.data.logical_structures.scores.impl.DoubleScore;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.descriptions.metrics.subsets.IConceptSubsetIDs;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.Functions;

public class DynamicFramingForSubsets extends RelativeAbstractSimilarityScorer<IConceptSubsetIDs>
		implements RelativeSubsetSimilarityScorer {

	public DynamicFramingForSubsets() {
	}

	@Override
	public DoubleScore apply(IConceptSubsetIDs conceptSubsetIds) {
		Integer genus = Functions.commonAncestor(classificationTree, conceptSubsetIds.getSubsetIDs());
		return new DoubleScore(getDefinitionCostOf(genus));
	}

	@Override
	public RelativeSubsetSimilarityScorer setAsContext(Tree<Integer, ADifferentiae> classificationTree) {
		this.classificationTree = classificationTree;
		return this;
	}

}
