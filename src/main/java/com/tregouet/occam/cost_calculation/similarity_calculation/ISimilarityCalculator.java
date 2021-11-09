package com.tregouet.occam.cost_calculation.similarity_calculation;

import java.util.List;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.opt.graph.sparse.SparseIntDirectedWeightedGraph;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.operators.IConjunctiveOperator;
import com.tregouet.tree_finder.data.Tree;

public interface ISimilarityCalculator {
	
	double getCoherenceScore();
	
	double getCoherenceScore(int[] catIDs);
	
	SparseIntDirectedWeightedGraph getSparseGraph();
	
	double howPrototypicalAmong(int catID, int[] otherCatIDs);
	
	double howProtoypical(int catID);
	
	double howSimilar(int catID1, int catID2);
	
	double howSimilarTo(int catID1, int catID2);
	
	void set(Tree<ICategory, DefaultEdge> categories, List<IConjunctiveOperator> conjunctiveOperators);

}
