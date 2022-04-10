package com.tregouet.occam.alg.scorers.similarity.impl;

import com.tregouet.occam.alg.scorers.Scored;
import com.tregouet.occam.alg.scorers.similarity.SimilarityScorer;
import com.tregouet.occam.data.logical_structures.orders.total.impl.DoubleScore;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.Functions;

public abstract class AbstractSimilarityScorer<R extends Scored<DoubleScore>> implements SimilarityScorer<R> {
	
	protected Tree<Integer, AbstractDifferentiae> classificationTree;
	
	public AbstractSimilarityScorer() {
	}
	
	protected Double getDefinitionCostOf(Integer conceptID) {
		Double definitionCost = 0.0;
		int currentConceptID = conceptID;
		int ontologicalCommitmentID = classificationTree.getRoot();
		while(currentConceptID != ontologicalCommitmentID) {
			AbstractDifferentiae currentConceptDefinition = 
					classificationTree.incomingEdgeOf(currentConceptID);
			definitionCost += currentConceptDefinition.weight();
			currentConceptID = classificationTree.getEdgeTarget(currentConceptDefinition);
		}
		return definitionCost;
	}	
	
	protected Double getContextualDefinitionCostOf(Integer conceptID, Integer frameConceptID) {
		if (Functions.lowerSet(classificationTree, conceptID).contains(frameConceptID)) {
			Double definitionCost = 0.0;
			int currentConceptID = conceptID;
			while(currentConceptID != frameConceptID) {
				AbstractDifferentiae currentConceptDefinition = 
						classificationTree.incomingEdgeOf(currentConceptID);
				definitionCost += currentConceptDefinition.weight();
				currentConceptID = classificationTree.getEdgeTarget(currentConceptDefinition);
			}
			return definitionCost;
		}
		return null;
	}	

}
