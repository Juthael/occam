package com.tregouet.occam.alg.cost_calc.similarity_calc.impl;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.alg.cost_calc.similarity_calc.ISimilarityCalculator;
import com.tregouet.occam.data.concepts.IClassification;

public class ContrastModel extends AbstractSimCalculator implements ISimilarityCalculator {

	public ContrastModel() {
	}

	public ContrastModel(IClassification classification) {
		super(classification);
	}

	@Override
	protected double howSimilar(Integer vertex1, Integer vertex2) {
		double similarity = 0.0;
		Set<Integer> edgesFromVertex1ToRoot = new HashSet<>(getEdgeChainToRootVertexFrom(vertex1));
		Set<Integer> edgesFromVertex2ToRoot = new HashSet<>(getEdgeChainToRootVertexFrom(vertex2));
		Set<Integer> intersection = new HashSet<>();
		Set<Integer> complement = new HashSet<>();
		intersection.addAll(edgesFromVertex1ToRoot);
		intersection.retainAll(edgesFromVertex2ToRoot);
		complement.addAll(edgesFromVertex1ToRoot);
		complement.addAll(edgesFromVertex2ToRoot);
		complement.removeAll(intersection);
		for (Integer edge : intersection)
			similarity += weightedTransitions.getEdgeWeight(edge);
		for (Integer edge : complement)
			similarity -= weightedTransitions.getEdgeWeight(edge);
		return similarity;
	}

	@Override
	protected double howSimilarTo(Integer vertex1, Integer vertex2) {
		double similarity = 0.0;
		Set<Integer> edgesFromVertex1ToRoot = new HashSet<>(getEdgeChainToRootVertexFrom(vertex1));
		Set<Integer> edgesFromVertex2ToRoot = new HashSet<>(getEdgeChainToRootVertexFrom(vertex2));
		Set<Integer> intersection = new HashSet<>();
		Set<Integer> vertex1Complement = new HashSet<>();
		for (Integer edge : edgesFromVertex1ToRoot) {
			if (edgesFromVertex2ToRoot.contains(edge))
				intersection.add(edge);
			else vertex1Complement.add(edge);
		}
		for (Integer edge : intersection)
			similarity += weightedTransitions.getEdgeWeight(edge);
		for (Integer edge : vertex1Complement)
			similarity -= weightedTransitions.getEdgeWeight(edge);
		return similarity;
	}

}
