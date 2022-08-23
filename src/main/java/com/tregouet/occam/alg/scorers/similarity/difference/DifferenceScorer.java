package com.tregouet.occam.alg.scorers.similarity.difference;

import com.tregouet.occam.data.representations.IRepresentation;

public interface DifferenceScorer {
	
	double apply(IRepresentation comparatison);

}
