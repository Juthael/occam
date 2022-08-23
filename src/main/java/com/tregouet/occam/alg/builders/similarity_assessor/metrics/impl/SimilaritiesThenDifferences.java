package com.tregouet.occam.alg.builders.similarity_assessor.metrics.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jgrapht.alg.util.UnorderedPair;

import com.tregouet.occam.alg.builders.similarity_assessor.metrics.SimilarityMetricsBuilder;
import com.tregouet.occam.data.modules.similarity.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.modules.similarity.metrics.impl.SimilarityMetrics;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConceptLattice;

public class SimilaritiesThenDifferences implements SimilarityMetricsBuilder {
	
	public static final SimilaritiesThenDifferences INSTANCE = new SimilaritiesThenDifferences();
	
	private SimilaritiesThenDifferences() {
	}

	@Override
	public ISimilarityMetrics apply(IConceptLattice conceptLattice,
			Map<UnorderedPair<Integer, Integer>, IRepresentation> dichotomies,
			Map<UnorderedPair<Integer, Integer>, IRepresentation> comparisons) {
		List<Integer> particularIDs = new ArrayList<>();
		for (IConcept particular : conceptLattice.getParticulars())
			particularIDs.add(particular.iD());
		Double[][] similarityMatrix = buildSimilarityMatrix(particularIDs, dichotomies);
		Double[][] asymmetricalSimilarityMatrix = buildAsymmetricalSimilarityMatrix(particularIDs, similarityMatrix);
		double[] typicalityVector = buildTypicalityVector(particularIDs, asymmetricalSimilarityMatrix);
		Double[][] differenceMatrix = buildDifferenceMatrix(particularIDs, comparisons);
		return new SimilarityMetrics(similarityMatrix, asymmetricalSimilarityMatrix, typicalityVector, differenceMatrix);
	}
	
	private static Double[][] buildDifferenceMatrix(List<Integer> particularIDs, 
			Map<UnorderedPair<Integer, Integer>, IRepresentation> comparisons) {
		int card = particularIDs.size();
		Double[][] differenceMatrix = new Double[card][card];
		for (Entry<UnorderedPair<Integer, Integer>, IRepresentation> comparison : comparisons.entrySet()) {
			UnorderedPair<Integer, Integer> pair = comparison.getKey();
			int x = particularIDs.indexOf(pair.getFirst());
			int y = particularIDs.indexOf(pair.getSecond());
			Double diffScore = SimilarityMetricsBuilder.differenceScorer().apply(comparison.getValue());
			differenceMatrix[x][y] = diffScore;
			differenceMatrix[y][x] = diffScore;
		}
		return differenceMatrix;
	}
	
	private static Double[][] buildAsymmetricalSimilarityMatrix(List<Integer> particularIDs, Double[][] similarityMatrix) {
		int card = particularIDs.size();
		Double[][] asymmSimMatrix = new Double[card][card];
		for (int i = 0 ; i < card ; i++) {
			for (int j = 0 ; j < card ; j++) {
				if (i != j)
					asymmSimMatrix[i][j] = 
							SimilarityMetricsBuilder.asymmetricalSimilarityScorer().apply(
									particularIDs.get(i), particularIDs.get(j), particularIDs, similarityMatrix);
			}
		}
		return asymmSimMatrix;
	}
	
	private static double[] buildTypicalityVector(List<Integer> particularIDs, Double[][] asymmetricalSimMatrix) {
		double[] typicalityVector = new double[particularIDs.size()];
		for (int i = 0 ; i < particularIDs.size() ; i++) {
			typicalityVector[i] = 
					SimilarityMetricsBuilder.typicalityScorer().apply(
							particularIDs.get(i), particularIDs, asymmetricalSimMatrix);			
		}
		return typicalityVector;
	}
	
	private static Double[][] buildSimilarityMatrix(List<Integer> particularIDs, 
			Map<UnorderedPair<Integer, Integer>, IRepresentation> dichotomies) {
		int card = particularIDs.size();
		Double[][] similarityMatrix = new Double[card][card];
		for (Entry<UnorderedPair<Integer, Integer>, IRepresentation> dichotomy : dichotomies.entrySet()) {
			UnorderedPair<Integer, Integer> pair = dichotomy.getKey();
			int x = particularIDs.indexOf(pair.getFirst());
			int y = particularIDs.indexOf(pair.getSecond());
			Double score = SimilarityMetricsBuilder.similarityScorer().apply(pair, dichotomy.getValue());
			similarityMatrix[x][y] = score;
			similarityMatrix[y][x] = score;
		}
		return similarityMatrix;
	}

}
