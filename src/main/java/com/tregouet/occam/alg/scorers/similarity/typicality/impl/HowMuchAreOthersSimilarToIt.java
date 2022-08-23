package com.tregouet.occam.alg.scorers.similarity.typicality.impl;

import java.util.List;

import com.tregouet.occam.alg.scorers.similarity.typicality.TypicalityScorer;

public class HowMuchAreOthersSimilarToIt implements TypicalityScorer {
	
	public static final HowMuchAreOthersSimilarToIt INSTANCE = new HowMuchAreOthersSimilarToIt();
	
	private HowMuchAreOthersSimilarToIt() {
	}

	@Override
	public double apply(Integer particularID, List<Integer> particularIDs, Double[][] asymmetricalSimMatrix) {
		int particularIdx = particularIDs.indexOf(particularID);
		double cardinal = (double) particularIDs.size();
		double similaritySum = 0.0;
		for (int i = 0 ; i < particularIDs.size() ; i++) {
			if (i != particularIdx) {
				similaritySum += asymmetricalSimMatrix[i][particularIdx];
			}
		}
		return similaritySum / (cardinal - 1);
	}

}
