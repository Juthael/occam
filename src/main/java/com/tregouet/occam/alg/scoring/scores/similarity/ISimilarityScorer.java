package com.tregouet.occam.alg.scoring.scores.similarity;

import java.util.List;
import java.util.Map;

import com.tregouet.occam.alg.scoring.scores.IScorer;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.concepts.IConcept;

public interface ISimilarityScorer extends IScorer<ISimilarityScorer, ITransitionFunction> {
	
	double[][] getAsymmetricalSimilarityMatrix();
	
	double getCoherenceScore();
	
	double getCoherenceScore(int[] conceptIDs);
	
	Map<Integer, Double> getConceptualCoherenceMap();
	
	List<IConcept> getListOfSingletons();
	
	double[][] getSimilarityMatrix();
	
	double[] getTypicalityArray();
	
	double howPrototypicalAmong(int conceptID, int[] otherConceptsIDs);
	
	double howProtoypical(int conceptID);
	
	double howSimilar(int conceptID1, int conceptID2);	
	
	double howSimilarTo(int conceptID1, int conceptID2);
	
	@Override
	ISimilarityScorer input(ITransitionFunction transitionFunction);

}
