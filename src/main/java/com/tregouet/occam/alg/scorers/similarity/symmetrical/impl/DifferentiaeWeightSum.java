package com.tregouet.occam.alg.scorers.similarity.symmetrical.impl;

import java.util.Set;

import org.jgrapht.alg.util.UnorderedPair;

import com.tregouet.occam.alg.scorers.similarity.symmetrical.SimilarityScorer;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;

public class DifferentiaeWeightSum implements SimilarityScorer {
	
	public static final DifferentiaeWeightSum INSTANCE = new DifferentiaeWeightSum();
	
	private DifferentiaeWeightSum() {
	}

	@Override
	public Double apply(UnorderedPair<Integer, Integer> comparedParticularIDs, IRepresentation dichotomisticRepresentation) {
		if (comparedParticularIDs.getFirst().equals(comparedParticularIDs.getSecond()))
			return null;
		Double simScore = 0.0;
		Tree<Integer, ADifferentiae> dichotomisticGraph = dichotomisticRepresentation.getDescription().asGraph();
		Set<Integer> alternative = dichotomisticGraph.getLeaves();
		for (Integer leaf : alternative) {
			simScore += dichotomisticGraph.incomingEdgeOf(leaf).weight();
		}
		return simScore;
	}

}
