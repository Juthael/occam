package com.tregouet.occam.alg.calculators.scores.similarity;

import java.util.List;
import java.util.Map;

import com.tregouet.occam.alg.calculators.scores.IScorer;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.concepts.IConcept;

public interface ISimilarityScorer extends IScorer<ISimilarityScorer, ITransitionFunction> {
	
	double getCoherenceScore();
	
	double getCoherenceScore(int[] conceptIDs);
	
	double howPrototypicalAmong(int conceptID, int[] otherConceptsIDs);
	
	double howProtoypical(int conceptID);
	
	double howSimilar(int conceptID1, int conceptID2);
	
	double howSimilarTo(int conceptID1, int conceptID2);
	
	double[][] getAsymmetricalSimilarityMatrix();
	
	Map<Integer, Double> getConceptualCoherenceMap();
	
	double[][] getSimilarityMatrix();
	
	double[] getTypicalityArray();	
	
	ISimilarityScorer input(ITransitionFunction transitionFunction);
	
	List<IConcept> getListOfSingletons();

}
