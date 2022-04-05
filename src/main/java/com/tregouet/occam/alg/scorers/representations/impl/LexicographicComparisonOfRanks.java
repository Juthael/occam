package com.tregouet.occam.alg.scorers.representations.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.alg.scorers.representations.RepresentationScorer;
import com.tregouet.occam.data.logical_structures.scores.impl.LexicographicScore;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.properties.AbstractDifferentiae;

public class LexicographicComparisonOfRanks implements RepresentationScorer<LexicographicScore> {

	@Override
	public LexicographicScore apply(IRepresentation<LexicographicScore> representation) {
		int maxRank = 0;
		Set<AbstractDifferentiae> differentiae = new HashSet<>(representation.getContextDescription().asGraph().edgeSet());
		for (AbstractDifferentiae diff : differentiae) {
			if (maxRank < diff.rank())
				maxRank = diff.rank();
		}
		Double[] valuesArray = new Double[maxRank + 1];
		for (AbstractDifferentiae diff : differentiae) {
			valuesArray[diff.rank()] += diff.weight();
		}
		return new LexicographicScore(Arrays.asList(valuesArray));
	}

}
