package com.tregouet.occam.alg.scorers.similarity.impl;

import java.util.Set;

import com.tregouet.occam.alg.scorers.similarity.RelativeSubsetSimilarityScorer;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.Functions;

public class DynamicFramingForSubsets extends RelativeAbstractSimilarityScorer
		implements RelativeSubsetSimilarityScorer {

	public DynamicFramingForSubsets() {
	}

	@Override
	public double score(Set<Integer> conceptIDs) {
		Integer genus = Functions.commonAncestor(classificationTree, conceptIDs);
		return getDefinitionCostOf(genus);
	}

	@Override
	public RelativeSubsetSimilarityScorer setAsContext(Tree<Integer, ADifferentiae> classificationTree) {
		this.classificationTree = classificationTree;
		return this;
	}

}
