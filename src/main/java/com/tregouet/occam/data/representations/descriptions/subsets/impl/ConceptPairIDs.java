package com.tregouet.occam.data.representations.descriptions.subsets.impl;

import com.tregouet.occam.alg.scorers.scores.DoubleScore;
import com.tregouet.occam.data.representations.descriptions.subsets.IConceptPairIDs;

public class ConceptPairIDs implements IConceptPairIDs {
	
	private final int targetID;
	private final int sourceID;
	private DoubleScore asymmetricalSimilarityScore = null;
	
	public ConceptPairIDs(int targetID, int sourceID) {
		this.targetID = targetID;
		this.sourceID = sourceID;
	}

	@Override
	public DoubleScore score() {
		return asymmetricalSimilarityScore;
	}

	@Override
	public void setScore(DoubleScore score) {
		this.asymmetricalSimilarityScore = score;
	}

	@Override
	public int first() {
		return targetID;
	}

	@Override
	public int second() {
		return sourceID;
	}

}
