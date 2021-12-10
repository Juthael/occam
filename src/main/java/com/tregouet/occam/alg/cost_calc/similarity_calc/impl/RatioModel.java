package com.tregouet.occam.alg.cost_calc.similarity_calc.impl;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.cost_calc.similarity_calc.ISimilarityCalculator;
import com.tregouet.occam.data.concepts.IClassification;

public class RatioModel extends AbstractSimCalculator implements ISimilarityCalculator {

	public RatioModel() {
	}

	public RatioModel(IClassification classification) {
		super(classification);
	}

	@Override
	protected double howSimilar(Integer vertex1, Integer vertex2) {
		double similarity = 0.0;
		Set<Integer> edgesFromVertex1ToRoot = new HashSet<>(getEdgeChainToRootVertexFrom(vertex1));
		Set<Integer> edgesFromVertex2ToRoot = new HashSet<>(getEdgeChainToRootVertexFrom(vertex2));
		Set<Integer> intersection = new HashSet<>();
		Set<Integer> union = new HashSet<>(edgesFromVertex1ToRoot);
		for (Integer edge : edgesFromVertex2ToRoot) {
			if (!union.add(edge))
				intersection.add(edge);
		}
		double unionWeightSum = weightSum(union);
		if (unionWeightSum != 0) {
			double intersectionWeightSum = weightSum(intersection);
			similarity = intersectionWeightSum / unionWeightSum;
		}
		return similarity;
	}

	@Override
	protected double howSimilarTo(Integer vertex1, Integer vertex2) {
		double similarity = 0.0;
		Set<Integer> edgesFromVertex1ToRoot = new HashSet<>(getEdgeChainToRootVertexFrom(vertex1));
		Set<Integer> edgesFromVertex2ToRoot = new HashSet<>(getEdgeChainToRootVertexFrom(vertex2));
		Set<Integer> intersection = Sets.intersection(edgesFromVertex1ToRoot, edgesFromVertex2ToRoot);
		double vertex1WeightSum = weightSum(edgesFromVertex1ToRoot);
		if (vertex1WeightSum != 0) {
			double intersectionWeightSum = weightSum(intersection);
			similarity = intersectionWeightSum / vertex1WeightSum;
		}
		return similarity;
	}
	
	private double weightSum(Set<Integer> edges) {
		double weightSum = 0.0;
		for (Integer edge : edges) {
			weightSum += weightedTransitions.getEdgeWeight(edge);
		}
		return weightSum;
	}	

}
