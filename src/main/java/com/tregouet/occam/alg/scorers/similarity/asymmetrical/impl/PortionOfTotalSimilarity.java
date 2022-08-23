package com.tregouet.occam.alg.scorers.similarity.asymmetrical.impl;

import java.util.List;

import com.tregouet.occam.alg.scorers.similarity.asymmetrical.AsymmetricalSimilarityScorer;

public class PortionOfTotalSimilarity implements AsymmetricalSimilarityScorer {
	
	public static final PortionOfTotalSimilarity INSTANCE = new PortionOfTotalSimilarity();
	
	private PortionOfTotalSimilarity() {
	}

	@Override
	public Double apply(int comparedParticularID, int refParticularID, List<Integer> particularIDs,
			Double[][] similarityMatrix) {
		if(comparedParticularID == refParticularID)
			return null;
		int comparedIdx = particularIDs.indexOf(comparedParticularID);
		int refIdx = particularIDs.indexOf(refParticularID);
		double refSimilarity = 0.0;
		double totalSimilarity = 0.0;
		for (int i = 0 ; i < particularIDs.size() ; i++) {
			if (i != comparedIdx) {
				double iSim = similarityMatrix[comparedIdx][i];
				if (i == refIdx)
					refSimilarity = iSim;
				totalSimilarity += iSim;
			}
		}
		return refSimilarity / totalSimilarity;
	}

}
