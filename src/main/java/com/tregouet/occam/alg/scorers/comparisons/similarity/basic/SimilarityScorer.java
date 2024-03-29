package com.tregouet.occam.alg.scorers.comparisons.similarity.basic;

import java.util.function.BiFunction;

import org.jgrapht.alg.util.UnorderedPair;

import com.tregouet.occam.data.structures.representations.IRepresentation;

public interface SimilarityScorer extends BiFunction<UnorderedPair<Integer, Integer>, IRepresentation, Double> {

}
