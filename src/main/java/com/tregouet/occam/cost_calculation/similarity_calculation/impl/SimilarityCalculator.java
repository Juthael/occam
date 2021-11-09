package com.tregouet.occam.cost_calculation.similarity_calculation.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.alg.util.Triple;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.opt.graph.sparse.SparseIntDirectedWeightedGraph;

import com.google.common.primitives.Ints;
import com.tregouet.occam.cost_calculation.similarity_calculation.ISimilarityCalculator;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.operators.IConjunctiveOperator;
import com.tregouet.tree_finder.data.Tree;

public class SimilarityCalculator implements ISimilarityCalculator {

	private final int[] topologicalSortingOfCatIDs;
	private final Integer[] objCatIdxInTopologicalSorting;
	private final SparseIntDirectedWeightedGraph weightedTransitions;
	
	public SimilarityCalculator(Tree<ICategory, DefaultEdge> categories, 
			List<IConjunctiveOperator> conjunctiveOperators) {
		int topoIdx = 0;
		Integer objIndex = 0;
		int nbOfObjects = categories.getLeaves().size();
		int nbOfCategories = categories.vertexSet().size();
		topologicalSortingOfCatIDs = new int[nbOfCategories];
		objCatIdxInTopologicalSorting = new Integer[nbOfObjects];
		for (ICategory category : categories.getTopologicalOrder()) {
			topologicalSortingOfCatIDs[topoIdx] = category.getID();
			if (objIndex < nbOfObjects && category.type() == ICategory.OBJECT) {
				objCatIdxInTopologicalSorting[objIndex] = topoIdx;
				objIndex++;
			}
			topoIdx++;
		}
		List<Triple<Integer, Integer, Double>> edges = new ArrayList<>();
		for (IConjunctiveOperator op : conjunctiveOperators) {
			//Any state has a category as one of its component. This category ID is the state ID.
			Integer operatingStateIndex = indexOf(op.getOperatingState().getStateID());
			Integer nextStateIndex = indexOf(op.getNextState().getStateID());
			Double informativity = op.getInformativity();
			edges.add(new Triple<Integer, Integer, Double>(operatingStateIndex, nextStateIndex, informativity));
		}
		weightedTransitions = new SparseIntDirectedWeightedGraph(nbOfCategories, edges);
	}

	@Override
	public double getCoherenceScore() {
		return getCoherenceScore(objCatIdxInTopologicalSorting);
	}

	@Override
	public double getCoherenceScore(int[] catIDs) {
		Integer[] vertices = new Integer[catIDs.length];
		for (int i = 0 ; i < catIDs.length ; i++) {
			vertices[i] = indexOf(catIDs[i]);
		}
		return getCoherenceScore(vertices);
	}
	
	/**
	 * Public for test use.
	 * @param vertex
	 * @return
	 */
	public Set<Integer> getReacheableEdgesFrom(int catID) {
		return getReacheableEdgesFrom(indexOf(catID));
	}	
	
	@Override
	public SparseIntDirectedWeightedGraph getSparseGraph() {
		return weightedTransitions;
	}

	@Override
	public double howPrototypicalAmong(int catID, int[] otherCatIDs) {
		Integer vertex = indexOf(catID);
		Integer[] otherVertices = new Integer[otherCatIDs.length];
		for (int i = 0 ; i < otherCatIDs.length ; i++) {
			otherVertices[i] = indexOf(otherCatIDs[i]);
		}
		return howPrototypicalAmong(vertex, otherVertices);
	}

	@Override
	public double howProtoypical(int catID) {
		return howPrototypicalAmong(indexOf(catID), objCatIdxInTopologicalSorting);
	}
	
	@Override
	public double howSimilar(int catID1, int catID2) {
		return howSimilar(indexOf(catID1), indexOf(catID2));
	}

	@Override
	public double howSimilarTo(int catID1, int catID2) {
		return howSimilarTo(indexOf(catID1), indexOf(catID2));
	}
	
	/**
	 * Public for test use
	 * @param catID
	 * @return
	 */
	public Integer indexOf(int catID) {
		return Ints.indexOf(topologicalSortingOfCatIDs, catID);
	}
	
	private double getCoherenceScore(Integer[] vertices) {
		double similaritySum = 0.0;
		double n = vertices.length;
		for (int i = 0 ; i < vertices.length - 1 ; i++) {
			for (int j = i + 1 ; j < vertices.length ; j++) {
				similaritySum += howSimilar(vertices[i], vertices[j]);
			}
		}
		return similaritySum / ((n*(n-1))/2);
	}
	
	private Set<Integer> getReacheableEdgesFrom(Integer vertex) {
		return getReacheableEdgesFrom(vertex, new HashSet<Integer>());
	}

	private Set<Integer> getReacheableEdgesFrom(Integer vertex, Set<Integer> alreadyFound) {
		if (weightedTransitions.outDegreeOf(vertex) != 0) {
			Set<Integer> nextEdges = weightedTransitions.outgoingEdgesOf(vertex);
			for (Integer nextEdge : nextEdges) {
				if (alreadyFound.add(nextEdge))
					alreadyFound.addAll(
							getReacheableEdgesFrom(weightedTransitions.getEdgeTarget(nextEdge), alreadyFound));
			}
		}
		return alreadyFound;
	}
	
	private double howPrototypicalAmong(Integer vertex, Integer[] others) {
		double similarityToParameterSum = 0.0;
		int nbOfComparisons = 0;
		for (Integer other : others) {
			if (!vertex.equals(other)) {
				similarityToParameterSum += howSimilarTo(other, vertex);
				nbOfComparisons++;
			}
		}
		return similarityToParameterSum / nbOfComparisons;
	}

	private double howSimilar(Integer vertex1, Integer vertex2) {
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
	
	private double howSimilarTo(Integer vertex1, Integer vertex2) {
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
