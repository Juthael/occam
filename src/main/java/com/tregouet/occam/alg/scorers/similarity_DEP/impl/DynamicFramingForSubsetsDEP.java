package com.tregouet.occam.alg.scorers.similarity_DEP.impl;

import java.util.Set;

import com.tregouet.occam.alg.scorers.similarity_DEP.RelativeSubsetSimilarityScorerDEP;
import com.tregouet.occam.data.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.Functions;

public class DynamicFramingForSubsetsDEP extends RelativeAbstractSimilarityScorerDEP
		implements RelativeSubsetSimilarityScorerDEP {

	public DynamicFramingForSubsetsDEP() {
	}

	@Override
	public double score(Set<Integer> conceptIDs) {
		Integer genus = Functions.commonAncestor(classificationTree, conceptIDs);
		return getDefinitionCostOf(genus);
	}

	@Override
	public RelativeSubsetSimilarityScorerDEP setAsContext(Tree<Integer, ADifferentiae> classificationTree) {
		this.classificationTree = classificationTree;
		return this;
	}

}
