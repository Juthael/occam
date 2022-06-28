package com.tregouet.occam.alg.scorers.similarity.impl;

import com.tregouet.occam.alg.scorers.similarity.RelativeSimilarityScorer;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;

public abstract class RelativeAbstractSimilarityScorer implements RelativeSimilarityScorer {

	protected Tree<Integer, ADifferentiae> classificationTree;

	public RelativeAbstractSimilarityScorer() {
	}

	protected Double getDefinitionCostOf(Integer conceptID) {
		double definitionCost = 0.0;
		int currentConceptID = conceptID;
		int ontologicalCommitmentID = classificationTree.getRoot();
		while (currentConceptID != ontologicalCommitmentID) {
			ADifferentiae currentConceptDefinition = classificationTree.incomingEdgeOf(currentConceptID);
			definitionCost += currentConceptDefinition.weight();
			currentConceptID = classificationTree.getEdgeSource(currentConceptDefinition);
		}
		return definitionCost;
	}

}
