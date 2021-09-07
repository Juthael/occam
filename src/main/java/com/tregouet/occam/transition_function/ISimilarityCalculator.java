package com.tregouet.occam.transition_function;

import java.util.List;

public interface ISimilarityCalculator {
	
	double getCoherenceScore();
	
	double getCoherenceScore(List<Integer> objectIndexes);
	
	double howSimilar(int onjIdx1, int objIndex2);
	
	double howSimilarTo(int objIdx1, int objIdx2);
	
	double howProtoypical(int objIdx1);
	
	double howPrototypicalAmong(int objIdx, List<Integer> objSubset);

}
