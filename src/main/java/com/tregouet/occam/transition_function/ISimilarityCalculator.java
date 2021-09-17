package com.tregouet.occam.transition_function;

import org.jgrapht.opt.graph.sparse.SparseIntDirectedWeightedGraph;

public interface ISimilarityCalculator {
	
	double getCoherenceScore();
	
	double getCoherenceScore(int[] catIDs);
	
	double howSimilar(int catID1, int catID2);
	
	double howSimilarTo(int catID1, int catID2);
	
	double howProtoypical(int catID);
	
	double howPrototypicalAmong(int catID, int[] otherCatIDs);
	
	SparseIntDirectedWeightedGraph getSparseGraph();

}
