package com.tregouet.occam.alg.builders.comparator.metrics;

import java.util.Map;

import org.jgrapht.alg.util.UnorderedPair;

import com.tregouet.occam.alg.scorers.ScorersAbstractFactory;
import com.tregouet.occam.alg.scorers.comparisons.difference.DifferenceScorer;
import com.tregouet.occam.alg.scorers.comparisons.similarity.asymmetrical.AsymmetricalSimilarityScorer;
import com.tregouet.occam.alg.scorers.comparisons.similarity.basic.SimilarityScorer;
import com.tregouet.occam.alg.scorers.comparisons.similarity.typicality.TypicalityScorer;
import com.tregouet.occam.data.modules.comparison.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConceptLattice;

public interface SimilarityMetricsBuilder {

	ISimilarityMetrics apply(IConceptLattice conceptLattice, Map<UnorderedPair<Integer, Integer>, IRepresentation> dichotomies,
			Map<UnorderedPair<Integer, Integer>, IRepresentation> comparisons);

	public static AsymmetricalSimilarityScorer asymmetricalSimilarityScorer() {
		return ScorersAbstractFactory.INSTANCE.getAsymmetricalSimilarityScorer();
	}

	public static DifferenceScorer differenceScorer() {
		return ScorersAbstractFactory.INSTANCE.getDifferenceScorer();
	}

	public static SimilarityScorer similarityScorer() {
		return ScorersAbstractFactory.INSTANCE.getSimilarityScorer();
	}

	public static TypicalityScorer typicalityScorer() {
		return ScorersAbstractFactory.INSTANCE.getTypicalityScorer();
	}

}
