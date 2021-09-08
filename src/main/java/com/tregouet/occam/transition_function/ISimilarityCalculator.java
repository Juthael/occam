package com.tregouet.occam.transition_function;

import java.util.List;

public interface ISimilarityCalculator {
	
	double getCoherenceScore();
	
	double getCoherenceScore(List<Integer> idxes);
	
	double howSimilar(Integer idx1, Integer idx2);
	
	double howSimilarTo(Integer idx1, Integer idx2);
	
	double howProtoypical(Integer idx);
	
	double howPrototypicalAmong(Integer idx, List<Integer> objSubset);

}
