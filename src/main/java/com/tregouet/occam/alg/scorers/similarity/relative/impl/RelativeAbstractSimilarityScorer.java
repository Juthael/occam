package com.tregouet.occam.alg.scorers.similarity.relative.impl;

import com.tregouet.occam.alg.scorers.Scored;
import com.tregouet.occam.alg.scorers.similarity.relative.RelativeSimilarityScorer;
import com.tregouet.occam.data.logical_structures.scores.IDoubleScore;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.Functions;

public abstract class RelativeAbstractSimilarityScorer<R extends Scored<IDoubleScore>> implements RelativeSimilarityScorer<R> {

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
