package com.tregouet.occam.data.problem_space.states.descriptions.metrics.subsets.impl;

import java.util.Set;

import com.tregouet.occam.data.logical_structures.scores.IDoubleScore;
import com.tregouet.occam.data.problem_space.states.descriptions.metrics.subsets.IConceptSubsetIDs;

public class ConceptSubsetIDs implements IConceptSubsetIDs {

	Set<Integer> subsetIDs;
	IDoubleScore similarityScore = null;

	public ConceptSubsetIDs(Set<Integer> subsetIDs) {
		this.subsetIDs = subsetIDs;
	}

	@Override
	public Set<Integer> getSubsetIDs() {
		return subsetIDs;
	}

	@Override
	public IDoubleScore score() {
		return similarityScore;
	}

	@Override
	public void setScore(IDoubleScore score) {
		this.similarityScore = score;
	}

}
