package com.tregouet.occam.alg.cost_calc.similarity_calc;

import java.util.List;

import org.jgrapht.opt.graph.sparse.SparseIntDirectedWeightedGraph;

import com.tregouet.occam.data.abstract_machines.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.tree_finder.data.Tree;

public interface ISimilarityCalculator {
	
	double getCoherenceScore();
	
	double getCoherenceScore(int[] catIDs);
	
	SparseIntDirectedWeightedGraph getSparseGraph();
	
	double howPrototypicalAmong(int catID, int[] otherCatIDs);
	
	double howProtoypical(int catID);
	
	double howSimilar(int catID1, int catID2);
	
	double howSimilarTo(int catID1, int catID2);
	
	void set(Tree<IConcept, IsA> concepts, List<IConjunctiveTransition> conjunctiveTransitions);

}
