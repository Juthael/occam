package com.tregouet.occam.alg.builders.pb_space.metrics;

import java.util.function.Function;

import com.tregouet.occam.data.problem_space.IProblemSpace;
import com.tregouet.occam.data.problem_space.metrics.ISimilarityMetrics;

public interface SimilarityMetricsBuilder extends Function<IProblemSpace, ISimilarityMetrics> {

}
