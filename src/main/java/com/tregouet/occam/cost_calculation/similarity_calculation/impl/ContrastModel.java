package com.tregouet.occam.cost_calculation.similarity_calculation.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.cost_calculation.similarity_calculation.ISimilarityCalculator;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.operators.IConjunctiveOperator;
import com.tregouet.tree_finder.data.Tree;

public class ContrastModel extends AbstractSimCalculator implements ISimilarityCalculator {

	public ContrastModel(Tree<ICategory, DefaultEdge> categories, 
			List<IConjunctiveOperator> conjunctiveOperators) {
		super(categories, conjunctiveOperators);
	}
	
	public ContrastModel() {
	}

	@Override
	protected double howSimilar(Integer vertex1, Integer vertex2) {
		double similarity = 0.0;
		Set<Integer> edgesFromCatID1ToRoot = getReacheableEdgesFrom(vertex1);
		Set<Integer> edgesFromCatID2ToRoot = getReacheableEdgesFrom(vertex2);
		Set<Integer> union = new HashSet<>();
		Set<Integer> complement = new HashSet<>();
		union.addAll(edgesFromCatID1ToRoot);
		union.retainAll(edgesFromCatID2ToRoot);
		complement.addAll(edgesFromCatID1ToRoot);
		complement.addAll(edgesFromCatID2ToRoot);
		complement.removeAll(union);
		for (Integer edge : union)
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
		Set<Integer> union = new HashSet<>();
		Set<Integer> catID1ToRootMinusUnion = new HashSet<>();
		for (Integer edge : edgesFromCatID1ToRoot) {
			if (edgesFromCatID2ToRoot.contains(edge))
				union.add(edge);
			else catID1ToRootMinusUnion.add(edge);
		}
		for (Integer edge : union)
			similarity += weightedTransitions.getEdgeWeight(edge);
		for (Integer edge : catID1ToRootMinusUnion)
			similarity -= weightedTransitions.getEdgeWeight(edge);
		return similarity;
	}

}
