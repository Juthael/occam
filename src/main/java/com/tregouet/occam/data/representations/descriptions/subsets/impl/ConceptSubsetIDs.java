package com.tregouet.occam.data.representations.descriptions.subsets.impl;

import java.util.Set;

import com.tregouet.occam.data.logical_structures.scores.impl.DoubleScore;
import com.tregouet.occam.data.representations.descriptions.subsets.IConceptSubsetIDs;

public class ConceptSubsetIDs implements IConceptSubsetIDs {
	
	Set<Integer> subsetIDs;
	DoubleScore similarityScore = null;

	public ConceptSubsetIDs(Set<Integer> subsetIDs) {
		this.subsetIDs = subsetIDs;
	}
	
	@Override
	public DoubleScore score() {
		return similarityScore;
	}

	@Override
	public void setScore(DoubleScore score) {
		this.similarityScore = score;
	}

	@Override
	public Set<Integer> getSubsetIDs() {
		return subsetIDs;
	}

}
