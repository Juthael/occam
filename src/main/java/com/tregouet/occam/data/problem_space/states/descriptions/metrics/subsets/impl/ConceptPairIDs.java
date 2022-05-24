package com.tregouet.occam.data.problem_space.states.descriptions.metrics.subsets.impl;

import com.tregouet.occam.data.logical_structures.scores.impl.DoubleScore;
import com.tregouet.occam.data.problem_space.states.descriptions.metrics.subsets.IConceptPairIDs;

public class ConceptPairIDs implements IConceptPairIDs {

	private final int targetID;
	private final int sourceID;
	private DoubleScore asymmetricalSimilarityScore = null;

	public ConceptPairIDs(int targetID, int sourceID) {
		this.targetID = targetID;
		this.sourceID = sourceID;
	}

	@Override
	public int first() {
		return targetID;
	}

	@Override
	public DoubleScore score() {
		return asymmetricalSimilarityScore;
	}

	@Override
	public int second() {
		return sourceID;
	}

	@Override
	public void setScore(DoubleScore score) {
		this.asymmetricalSimilarityScore = score;
	}

}