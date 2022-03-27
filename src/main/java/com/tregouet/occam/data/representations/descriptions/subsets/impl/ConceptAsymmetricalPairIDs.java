package com.tregouet.occam.data.representations.descriptions.subsets.impl;

import com.tregouet.occam.alg.scorers.scores.DoubleScore;
import com.tregouet.occam.data.representations.descriptions.subsets.IConceptAsymmetricalPairIDs;

public class ConceptAsymmetricalPairIDs implements IConceptAsymmetricalPairIDs {
	
	private final int targetID;
	private final int sourceID;
	private DoubleScore asymmetricalSimilarityScore = null;
	
	public ConceptAsymmetricalPairIDs(int targetID, int sourceID) {
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
	public int getTargetID() {
		return targetID;
	}

	@Override
	public int getSourceID() {
		return sourceID;
	}

}
