package com.tregouet.occam.data.problem_space.states.descriptions.metrics.subsets.impl;

import com.tregouet.occam.data.logical_structures.scores.IDoubleScore;
import com.tregouet.occam.data.problem_space.states.descriptions.metrics.subsets.IConceptPairIDs;

public class ConceptPairIDs implements IConceptPairIDs {

	private final int targetID;
	private final int sourceID;
	private IDoubleScore asymmetricalSimilarityScore = null;

	public ConceptPairIDs(int targetID, int sourceID) {
		this.targetID = targetID;
		this.sourceID = sourceID;
	}

	@Override
	public int first() {
		return targetID;
	}

	@Override
	public IDoubleScore score() {
		return asymmetricalSimilarityScore;
	}

	@Override
	public int second() {
		return sourceID;
	}

	@Override
	public void setScore(IDoubleScore score) {
		this.asymmetricalSimilarityScore = score;
	}

}
