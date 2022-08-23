package com.tregouet.occam.alg.scorers.similarity.symmetrical.impl;

import java.util.Set;

import org.jgrapht.alg.util.UnorderedPair;

import com.tregouet.occam.alg.scorers.similarity.symmetrical.SimilarityScorer;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;

public class CoeffFreeDifferentiaeWeightSum implements SimilarityScorer {
	
	public static final CoeffFreeDifferentiaeWeightSum INSTANCE = new CoeffFreeDifferentiaeWeightSum();
	
	private CoeffFreeDifferentiaeWeightSum() {
	}

	@Override
	public Double apply(UnorderedPair<Integer, Integer> comparedParticularIDs, IRepresentation dichotomisticRepresentation) {
		if (comparedParticularIDs.getFirst().equals(comparedParticularIDs.getSecond()))
			return null;
		Double simScore = 0.0;
		Tree<Integer, ADifferentiae> dichotomisticGraph = dichotomisticRepresentation.getDescription().asGraph();
		Set<Integer> alternative = dichotomisticGraph.getLeaves();
		for (Integer leaf : alternative) {
			simScore += dichotomisticGraph.incomingEdgeOf(leaf).getCoeffFreeWeight();
		}
		return simScore;
	}

}
