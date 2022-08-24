package com.tregouet.occam.alg.scorers.comparisons.similarity.asymmetrical;

import java.util.List;

public interface AsymmetricalSimilarityScorer {

	Double apply(int comparedParticularID, int refParticularID, List<Integer> particularIDs, Double[][] similarityMatrix);

}
