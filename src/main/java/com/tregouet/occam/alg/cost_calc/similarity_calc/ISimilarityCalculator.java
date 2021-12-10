package com.tregouet.occam.alg.cost_calc.similarity_calc;

import java.util.List;

import org.jgrapht.opt.graph.sparse.SparseIntDirectedWeightedGraph;

import com.tregouet.occam.alg.cost_calc.concept_derivation_cost.ConceptDerivationCostStrategy;
import com.tregouet.occam.alg.cost_calc.concept_derivation_cost.IConceptDerivationCostCalculator;
import com.tregouet.occam.data.abstract_machines.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.concepts.IClassification;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.tree_finder.data.Tree;

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
