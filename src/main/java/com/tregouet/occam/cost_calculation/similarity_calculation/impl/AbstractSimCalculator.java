package com.tregouet.occam.cost_calculation.similarity_calculation.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.alg.util.Triple;
import org.jgrapht.opt.graph.sparse.SparseIntDirectedWeightedGraph;

import com.google.common.primitives.Ints;
import com.tregouet.occam.cost_calculation.similarity_calculation.ISimilarityCalculator;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.occam.data.operators.IConjunctiveTransition;
import com.tregouet.tree_finder.data.Tree;

public abstract class AbstractSimCalculator implements ISimilarityCalculator {

	protected int[] topologicalSortingOfCatIDs;
	protected Integer[] objCatIdxInTopologicalOrder;
	protected SparseIntDirectedWeightedGraph weightedTransitions;
	
	public AbstractSimCalculator() {
	}
	
	public AbstractSimCalculator(Tree<IConcept, IsA> concepts, 
			List<IConjunctiveTransition> conjunctiveTransitions) {
		set(concepts, conjunctiveTransitions);
	}

	@Override
	public double getCoherenceScore() {
		return getCoherenceScore(objCatIdxInTopologicalOrder);
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
		return howPrototypicalAmong(indexOf(catID), objCatIdxInTopologicalOrder);
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
	
	@Override
	public void set(Tree<IConcept, IsA> concepts, List<IConjunctiveTransition> conjunctiveTransitions) {
		int topoIdx = 0;
		Integer objIndex = 0;
		int nbOfObjects = concepts.getLeaves().size();
		int nbOfCategories = concepts.vertexSet().size();
		topologicalSortingOfCatIDs = new int[nbOfCategories];
		objCatIdxInTopologicalOrder = new Integer[nbOfObjects];
		for (IConcept concept : concepts.getTopologicalOrder()) {
			topologicalSortingOfCatIDs[topoIdx] = concept.getID();
			if (objIndex < nbOfObjects && concept.type() == IConcept.SINGLETON) {
				objCatIdxInTopologicalOrder[objIndex] = topoIdx;
				objIndex++;
			}
			topoIdx++;
		}
		List<Triple<Integer, Integer, Double>> edges = new ArrayList<>();
		for (IConjunctiveTransition op : conjunctiveTransitions) {
			//Any state has a category as one of its component. This category ID is the state ID.
			Integer operatingStateIndex = indexOf(op.getOperatingState().getStateID());
			Integer nextStateIndex = indexOf(op.getNextState().getStateID());
			Double informativity = op.getInformativity();
			edges.add(new Triple<Integer, Integer, Double>(operatingStateIndex, nextStateIndex, informativity));
		}
		weightedTransitions = new SparseIntDirectedWeightedGraph(nbOfCategories, edges);
	}	
	
	protected Set<Integer> getReacheableEdgesFrom(Integer vertex) {
		return getReacheableEdgesFrom(vertex, new HashSet<Integer>());
	}

	abstract protected double howSimilar(Integer vertex1, Integer vertex2);	
	
	abstract protected double howSimilarTo(Integer vertex1, Integer vertex2);	
	
	private double getCoherenceScore(Integer[] vertices) {
		if (vertices.length == 1)
			return 1.0;
		double similaritySum = 0.0;
		double n = vertices.length;
		for (int i = 0 ; i < vertices.length - 1 ; i++) {
			for (int j = i + 1 ; j < vertices.length ; j++) {
				similaritySum += howSimilar(vertices[i], vertices[j]);
			}
		}
		return similaritySum / ((n*(n-1))/2);
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

}
