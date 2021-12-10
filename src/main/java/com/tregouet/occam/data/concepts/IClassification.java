package com.tregouet.occam.data.concepts;

import com.tregouet.occam.alg.cost_calc.similarity_calc.ISimilarityCalculator;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.tree_finder.data.Tree;

public interface IClassification extends Comparable<IClassification> {
	
	Tree<IConcept, IsA> getClassificationTree();
	
	double getCostOf(IsA relation);
	
	double getCoherenceScore();
	
	ISimilarityCalculator getSimilarityCalculator();
	
}
