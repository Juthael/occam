package com.tregouet.occam.alg.scorers.similarity.impl;

import java.util.Iterator;

import org.jgrapht.Graphs;

import com.tregouet.occam.alg.scorers.similarity.AsymmetricalSimilarityScorer;
import com.tregouet.occam.data.logical_structures.orders.total.impl.DoubleScore;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.descriptions.metrics.subsets.IConceptPairIDs;
import com.tregouet.tree_finder.utils.Functions;

public class AsymmetricalDynamicFraming extends AbstractSimilarityScorer<IConceptPairIDs> 
	implements AsymmetricalSimilarityScorer {
	
	public AsymmetricalDynamicFraming() {
	}

	@Override
	public DoubleScore apply(IConceptPairIDs iDPair) {
		Double genusDefinitionCost;
		Double targetDefinitionCost;
		Integer genusID = Functions.commonAncestor(classificationTree, iDPair.second(), iDPair.first());
		Integer targetAsGenusSpeciesID = findTargetAsGenusSpeciesID(iDPair.first(), genusID);
		genusDefinitionCost = getDefinitionCostOf(genusID);
		targetDefinitionCost = getDefinitionCostOf(targetAsGenusSpeciesID);
		return new DoubleScore(genusDefinitionCost / targetDefinitionCost);
	}
	
	@Override
	public AsymmetricalSimilarityScorer setAsContext(IDescription description) {
		classificationTree = description.asGraph();
		return this;
	}	
	
	private Integer findTargetAsGenusSpeciesID(Integer genusID, Integer targetID) {
		Integer targetAsGenusSpeciesID = null;
		Iterator<Integer> genusSuccessorIte = Graphs.successorListOf(classificationTree, genusID).iterator();
		while (targetAsGenusSpeciesID == null && genusSuccessorIte.hasNext()) {
			Integer nextSuccessor = genusSuccessorIte.next();
			if (nextSuccessor == targetID 
					|| classificationTree.getDescendants(nextSuccessor).contains(targetID)) {
				targetAsGenusSpeciesID = nextSuccessor;
			}
		}
		return targetAsGenusSpeciesID;
	}

}
