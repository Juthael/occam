package com.tregouet.occam.transition_function.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.alg.util.Triple;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.opt.graph.sparse.SparseIntDirectedWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.operators.IConjunctiveOperator;
import com.tregouet.occam.transition_function.ISimilarityCalculator;
import com.tregouet.tree_finder.data.InTree;

public class SimilarityCalculator implements ISimilarityCalculator {

	private final int nbOfObjects;
	private final List<Integer> breadthFirstCatIdxes = new ArrayList<>();
	private final SparseIntDirectedWeightedGraph inTree;
	
	public SimilarityCalculator(int nbOfObjects, InTree<ICategory, DefaultEdge> categories, 
			List<IConjunctiveOperator> conjunctiveOperators) {
		this.nbOfObjects = nbOfObjects;
		BreadthFirstIterator<ICategory, DefaultEdge> breadthFirstIte = new BreadthFirstIterator<>(categories);
		breadthFirstIte.forEachRemaining(c -> breadthFirstCatIdxes.add(c.getID()));
		List<Triple<Integer, Integer, Double>> edges = new ArrayList<>();
		for (IConjunctiveOperator op : conjunctiveOperators) {
			//a state and its associated category have the same ID
			Integer operatingStateIndex = breadthFirstCatIdxes.indexOf(op.getOperatingState().getStateID());
			Integer nextStateIndex = breadthFirstCatIdxes.indexOf(op.getNextState().getStateID());
			Double informativity = op.getInformativity();
			edges.add(new Triple<Integer, Integer, Double>(operatingStateIndex, nextStateIndex, informativity));
		}
		inTree = new SparseIntDirectedWeightedGraph(breadthFirstCatIdxes.size(), edges);
	}

	@Override
	public double getCoherenceScore() {
		return getCoherenceScore(breadthFirstCatIdxes.subList(0, nbOfObjects));
	}

	@Override
	public double getCoherenceScore(List<Integer> catIDs) {
		double similaritySum = 0.0;
		double n = (double) catIDs.size();
		for (int i = 0 ; i < catIDs.size() - 1 ; i++) {
			for (int j = i + 1 ; j < catIDs.size() ; j++) {
				similaritySum += howSimilar(catIDs.get(i), catIDs.get(j));
			}
		}
		return similaritySum / ((n*(n-1))/2);
	}

	@Override
	public double howSimilar(Integer catID1, Integer catID2) {
		double similarity = 0.0;
		Set<Integer> edgesFromCatID1ToRoot = getReacheableEdgesFrom(breadthFirstCatIdxes.indexOf(catID1));
		Set<Integer> edgesFromCatID2ToRoot = getReacheableEdgesFrom(breadthFirstCatIdxes.indexOf(catID2));
		Set<Integer> union = new HashSet<>();
		Set<Integer> complement = new HashSet<>();
		union.addAll(edgesFromCatID1ToRoot);
		union.retainAll(edgesFromCatID2ToRoot);
		complement.addAll(edgesFromCatID1ToRoot);
		complement.addAll(edgesFromCatID2ToRoot);
		complement.removeAll(union);
		for (Integer edge : union)
			similarity += inTree.getEdgeWeight(edge);
		for (Integer edge : complement)
			similarity -= inTree.getEdgeWeight(edge);
		return similarity;
	}

	@Override
	public double howSimilarTo(Integer catID1, Integer catID2) {
		double similarity = 0.0;
		Set<Integer> edgesFromCatID1ToRoot = getReacheableEdgesFrom(breadthFirstCatIdxes.indexOf(catID1));
		Set<Integer> edgesFromCatID2ToRoot = getReacheableEdgesFrom(breadthFirstCatIdxes.indexOf(catID2));
		Set<Integer> union = new HashSet<>();
		Set<Integer> catID1ToRootMinusUnion = new HashSet<>();
		for (Integer edge : edgesFromCatID1ToRoot) {
			if (edgesFromCatID2ToRoot.contains(edge))
				union.add(edge);
			else catID1ToRootMinusUnion.add(edge);
		}
		for (Integer edge : union)
			similarity += inTree.getEdgeWeight(edge);
		for (Integer edge : catID1ToRootMinusUnion)
			similarity -= inTree.getEdgeWeight(edge);
		return similarity;
	}

	@Override
	public double howProtoypical(Integer catID) {
		return howPrototypicalAmong(catID, breadthFirstCatIdxes.subList(0, nbOfObjects));
	}

	@Override
	public double howPrototypicalAmong(Integer catID, List<Integer> objCatIDs) {
		double similarityToParameterSum = 0.0;
		int nbOfComparisons = 0;
		for (Integer objCatID : objCatIDs) {
			if (!objCatID.equals(catID)) {
				similarityToParameterSum += howSimilarTo(objCatID, catID);
				nbOfComparisons++;
			}
		}
		return similarityToParameterSum / (double) nbOfComparisons;
	}
	
	private Set<Integer> getReacheableEdgesFrom(Integer vertex) {
		Set<Integer> edgesFromVertexToRoot = new HashSet<>();
		Integer currVertex = vertex;
		while (inTree.outDegreeOf(currVertex) != 0) {
			//since the graph is an in-tree, there can't be more than 1 outgoing edge
			Integer[] nextEdge = inTree.outgoingEdgesOf(currVertex).toArray(new Integer[1]);
			edgesFromVertexToRoot.add(nextEdge[0]);
			currVertex = inTree.getEdgeTarget(nextEdge[0]);
		}
		return edgesFromVertexToRoot;
	}

}
