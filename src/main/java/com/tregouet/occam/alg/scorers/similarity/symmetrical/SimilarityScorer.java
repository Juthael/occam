package com.tregouet.occam.alg.scorers.similarity.symmetrical;

import java.util.function.BiFunction;

import org.jgrapht.alg.util.UnorderedPair;

import com.tregouet.occam.data.representations.IRepresentation;

public interface SimilarityScorer extends BiFunction<UnorderedPair<Integer, Integer>, IRepresentation, Double> {

}
