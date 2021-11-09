package com.tregouet.occam.cost_calculation.similarity_calculation;

import org.jgrapht.opt.graph.sparse.SparseIntDirectedWeightedGraph;

public interface ISimilarityCalculator {
	
	double getCoherenceScore();
	
	double getCoherenceScore(int[] catIDs);
	
	SparseIntDirectedWeightedGraph getSparseGraph();
	
	double howPrototypicalAmong(int catID, int[] otherCatIDs);
	
	double howProtoypical(int catID);
	
	double howSimilar(int catID1, int catID2);
	
	double howSimilarTo(int catID1, int catID2);

}
