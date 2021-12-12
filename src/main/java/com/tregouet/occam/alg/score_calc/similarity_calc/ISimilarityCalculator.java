package com.tregouet.occam.alg.score_calc.similarity_calc;

import org.jgrapht.opt.graph.sparse.SparseIntDirectedWeightedGraph;

import com.tregouet.occam.data.concepts.IClassification;

public interface ISimilarityCalculator {
	
	double getCoherenceScore();
	
	double getCoherenceScore(int[] conceptIDs);
	
	SparseIntDirectedWeightedGraph getSparseGraph();
	
	double howPrototypicalAmong(int conceptID, int[] otherConceptsIDs);
	
	double howProtoypical(int conceptID);
	
	double howSimilar(int conceptID1, int conceptID2);
	
	double howSimilarTo(int conceptID1, int conceptID2);
	
	ISimilarityCalculator input(IClassification classification);

}
