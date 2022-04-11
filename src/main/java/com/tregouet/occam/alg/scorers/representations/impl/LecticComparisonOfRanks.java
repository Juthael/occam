package com.tregouet.occam.alg.scorers.representations.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.alg.scorers.representations.RepresentationScorer;
import com.tregouet.occam.data.logical_structures.orders.total.impl.LecticScore;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;

public class LecticComparisonOfRanks implements RepresentationScorer {
	
	public static final LecticComparisonOfRanks INSTANCE = new LecticComparisonOfRanks();
	
	private LecticComparisonOfRanks() {
	}

	@Override
	public LecticScore apply(IRepresentation representation) {
		int maxRank = 0;
		Set<AbstractDifferentiae> differentiae = new HashSet<>(representation.getDescription().asGraph().edgeSet());
		for (AbstractDifferentiae diff : differentiae) {
			if (maxRank < diff.rank())
				maxRank = diff.rank();
		}
		Double[] valuesArray = new Double[maxRank + 1];
		for (AbstractDifferentiae diff : differentiae) {
			valuesArray[diff.rank()] += diff.weight();
		}
		return new LecticScore(Arrays.asList(valuesArray));
	}

}
