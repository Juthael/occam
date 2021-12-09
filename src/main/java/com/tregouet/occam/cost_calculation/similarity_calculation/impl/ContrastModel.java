package com.tregouet.occam.cost_calculation.similarity_calculation.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.cost_calculation.similarity_calculation.ISimilarityCalculator;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.occam.data.transitions.IConjunctiveTransition;
import com.tregouet.tree_finder.data.Tree;

public class ContrastModel extends AbstractSimCalculator implements ISimilarityCalculator {

	public ContrastModel() {
	}
	
	public ContrastModel(Tree<IConcept, IsA> concepts, 
			List<IConjunctiveTransition> conjunctiveTransitions) {
		super(concepts, conjunctiveTransitions);
	}

	@Override
	protected double howSimilar(Integer vertex1, Integer vertex2) {
		double similarity = 0.0;
		Set<Integer> edgesFromCatID1ToRoot = getReacheableEdgesFrom(vertex1);
		Set<Integer> edgesFromCatID2ToRoot = getReacheableEdgesFrom(vertex2);
		Set<Integer> intersection = new HashSet<>();
		Set<Integer> complement = new HashSet<>();
		intersection.addAll(edgesFromCatID1ToRoot);
		intersection.retainAll(edgesFromCatID2ToRoot);
		complement.addAll(edgesFromCatID1ToRoot);
		complement.addAll(edgesFromCatID2ToRoot);
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
		Set<Integer> edgesFromCatID1ToRoot = getReacheableEdgesFrom(vertex1);
		Set<Integer> edgesFromCatID2ToRoot = getReacheableEdgesFrom(vertex2);
		Set<Integer> intersection = new HashSet<>();
		Set<Integer> catID1Complement = new HashSet<>();
		for (Integer edge : edgesFromCatID1ToRoot) {
			if (edgesFromCatID2ToRoot.contains(edge))
				intersection.add(edge);
			else catID1Complement.add(edge);
		}
		for (Integer edge : intersection)
			similarity += weightedTransitions.getEdgeWeight(edge);
		for (Integer edge : catID1Complement)
			similarity -= weightedTransitions.getEdgeWeight(edge);
		return similarity;
	}

}
