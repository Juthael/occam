package com.tregouet.occam.alg.scorers.similarity.typicality;

import java.util.List;

public interface TypicalityScorer {
	
	double apply(Integer particularID, List<Integer> particularIDs, Double[][] asymmetricalSimMatrix);

}
