package com.tregouet.occam.cost_calculation.similarity_calculation.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.cost_calculation.similarity_calculation.ISimilarityCalculator;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.occam.data.transitions.IConjunctiveTransition;
import com.tregouet.tree_finder.data.Tree;

public class RatioModel extends AbstractSimCalculator implements ISimilarityCalculator {

	public RatioModel() {
	}

	public RatioModel(Tree<IConcept, IsA> concepts, List<IConjunctiveTransition> conjunctiveTransitions) {
		super(concepts, conjunctiveTransitions);
	}

	@Override
	protected double howSimilar(Integer vertex1, Integer vertex2) {
		double similarity = 0.0;
		Set<Integer> edgesFromCatID1ToRoot = getReacheableEdgesFrom(vertex1);
		Set<Integer> edgesFromCatID2ToRoot = getReacheableEdgesFrom(vertex2);
		Set<Integer> intersection = new HashSet<>();
		Set<Integer> union = new HashSet<>(edgesFromCatID1ToRoot);
		for (Integer edge : edgesFromCatID2ToRoot) {
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
		Set<Integer> edgesFromCatID1ToRoot = getReacheableEdgesFrom(vertex1);
		Set<Integer> edgesFromCatID2ToRoot = getReacheableEdgesFrom(vertex2);
		Set<Integer> intersection = new HashSet<>();
		for (Integer edge : edgesFromCatID1ToRoot) {
			if (edgesFromCatID2ToRoot.contains(edge))
				intersection.add(edge);
		}
		double cat1WeightSum = weightSum(edgesFromCatID1ToRoot);
		if (cat1WeightSum != 0) {
			double intersectionWeightSum = weightSum(intersection);
			similarity = intersectionWeightSum / cat1WeightSum;
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
