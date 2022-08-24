package com.tregouet.occam.alg.scorers.comparisons.similarity.typicality;

import java.util.List;

public interface TypicalityScorer {

	double apply(Integer particularID, List<Integer> particularIDs, Double[][] asymmetricalSimMatrix);

}
