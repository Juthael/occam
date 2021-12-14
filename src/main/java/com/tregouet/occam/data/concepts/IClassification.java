package com.tregouet.occam.data.concepts;

import java.util.Map;

import com.tregouet.occam.alg.score_calc.similarity_calc.ISimilarityCalculator;
import com.tregouet.tree_finder.data.Tree;

public interface IClassification extends Comparable<IClassification> {
	
	double[][] getAsymmetricalSimilarityMatrix();
	
	Tree<IConcept, IIsA> getClassificationTree();
	
	double getCoherenceScore();
	
	Map<Integer, Double> getConceptualCoherenceMap();
	
	double getCostOf(IIsA relation);
	
	ISimilarityCalculator getSimilarityCalculator();
	
	double[][] getSimilarityMatrix();
	
	double[] getTypicalityArray();
	
}
