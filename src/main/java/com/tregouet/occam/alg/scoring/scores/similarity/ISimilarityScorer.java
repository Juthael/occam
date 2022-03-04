package com.tregouet.occam.alg.scoring.scores.similarity;

import java.util.List;
import java.util.Map;

import com.tregouet.occam.alg.scoring.scores.IScorer;
import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.denotations.IConcept;

public interface ISimilarityScorer extends IScorer<ISimilarityScorer, IAutomaton> {
	
	double[][] getAsymmetricalSimilarityMatrix();
	
	double getCoherenceScore();
	
	double getCoherenceScore(int[] cncptIDs);
	
	Map<Integer, Double> getConceptualCoherenceMap();
	
	List<IConcept> getListOfSingletons();
	
	double[][] getSimilarityMatrix();
	
	double[] getTypicalityArray();
	
	double howPrototypicalAmong(int cncptID, int[] otherCncptsIDs);
	
	double howProtoypical(int cncptID);
	
	double howSimilar(int cncptID1, int cncptID2);	
	
	double howSimilarTo(int cncptID1, int cncptID2);
	
	@Override
	ISimilarityScorer input(IAutomaton automaton);

}
