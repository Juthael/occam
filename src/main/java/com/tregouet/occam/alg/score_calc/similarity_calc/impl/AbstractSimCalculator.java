package com.tregouet.occam.alg.score_calc.similarity_calc.impl;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.alg.util.Triple;
import org.jgrapht.opt.graph.sparse.SparseIntDirectedWeightedGraph;

import com.google.common.primitives.Ints;
import com.tregouet.occam.alg.score_calc.similarity_calc.ISimilarityCalculator;
import com.tregouet.occam.data.concepts.IClassification;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.tree_finder.data.Tree;

public abstract class AbstractSimCalculator implements ISimilarityCalculator {

	protected int[] topologicalSortingOfConceptIDs = null;
	protected Integer[] successorIdxes = null;
	protected Integer[] singletonConceptsIdxes = null;
	protected SparseIntDirectedWeightedGraph weightedTransitions = null;
	Integer rootVertex = null;
	
	public AbstractSimCalculator() {
	}
	
	public AbstractSimCalculator(IClassification classification) {
		input(classification);
	}	

	@Override
	public double getCoherenceScore() {
		Integer[] vertices = new Integer[topologicalSortingOfConceptIDs.length];
		for (int i = 0 ; i < topologicalSortingOfConceptIDs.length ; i++)
			vertices[i] = i;
		return getCoherenceScore(vertices);
	}

	@Override
	public double getCoherenceScore(int[] conceptIDs) {
		Integer[] vertices = new Integer[conceptIDs.length];
		for (int i = 0 ; i < conceptIDs.length ; i++) {
			vertices[i] = indexOf(conceptIDs[i]);
		}
		return getCoherenceScore(vertices);
	}

	@Override
	public SparseIntDirectedWeightedGraph getSparseGraph() {
		return weightedTransitions;
	}
	
	@Override
	public double howPrototypicalAmong(int conceptID, int[] otherConceptsIDs) {
		Integer vertex = indexOf(conceptID);
		Integer[] otherVertices = new Integer[otherConceptsIDs.length];
		for (Integer i = 0 ; i < otherConceptsIDs.length ; i++) {
			otherVertices[i] = indexOf(otherConceptsIDs[i]);
		}
		return howPrototypicalAmong(vertex, otherVertices);
	}

	@Override
	public double howProtoypical(int conceptID) {
		int nbOfConcepts = topologicalSortingOfConceptIDs.length;
		Integer[] vertices = new Integer[nbOfConcepts];
		for (Integer i = 0 ; i < nbOfConcepts ; i++)
			vertices[i] = i;
		return howPrototypicalAmong(indexOf(conceptID), vertices);
	}

	@Override
	public double howSimilar(int conceptID1, int conceptID2) {
		return howSimilar(indexOf(conceptID1), indexOf(conceptID2));
	}

	@Override
	public double howSimilarTo(int conceptID1, int conceptID2) {
		return howSimilarTo(indexOf(conceptID1), indexOf(conceptID2));
	}

	/**
	 * Public for test use
	 * @param conceptID
	 * @return
	 */
	public Integer indexOf(int conceptID) {
		return Ints.indexOf(topologicalSortingOfConceptIDs, conceptID);
	}
	
	@Override
	public ISimilarityCalculator input(IClassification classification) {
		Tree<IConcept, IsA> classTree = classification.getClassificationTree();
		List<IConcept> topoOrderOverConcepts = classTree.getTopologicalOrder();
		Integer objIndex = 0;
		int nbOfObjects = classTree.getLeaves().size();
		int nbOfConcepts = topoOrderOverConcepts.size();
		topologicalSortingOfConceptIDs = new int[nbOfConcepts];
		singletonConceptsIdxes = new Integer[nbOfObjects];
		for (int i = 0 ; i < nbOfConcepts ; i++) {
			IConcept iConcept = topoOrderOverConcepts.get(i);
			topologicalSortingOfConceptIDs[i] = iConcept.getID();
			if (objIndex < nbOfObjects && iConcept.type() == IConcept.SINGLETON) {
				singletonConceptsIdxes[objIndex++] = i;
			}
		}
		rootVertex = topologicalSortingOfConceptIDs.length - 1;
		List<Triple<Integer, Integer, Double>> edges = new ArrayList<>();
		for (IsA derivation : classTree.edgeSet()) {
			Integer genusIdx = indexOf(classTree.getEdgeTarget(derivation).getID());
			Integer speciesIdx = indexOf(classTree.getEdgeSource(derivation).getID());
			edges.add(new Triple<>(speciesIdx, genusIdx, classification.getCostOf(derivation)));
		}
		weightedTransitions = new SparseIntDirectedWeightedGraph(nbOfConcepts, edges);
		//the root has no successor, others have only 1 since the graph is an inverted tree
		successorIdxes = new Integer[nbOfConcepts - 1];
		for (int i = 0 ; i < nbOfConcepts - 1 ; i++) {
			successorIdxes[i] = Graphs.successorListOf(weightedTransitions, i).get(0);
		}
		return this;
	}	
	
	protected List<Integer> getEdgeChainToRootFrom(int conceptID) {
		return getEdgeChainToRootVertexFrom(indexOf(conceptID));
	}		

	protected List<Integer> getEdgeChainToRootVertexFrom(Integer vertex) {
		if (vertex == rootVertex)
			return new ArrayList<>();
		List<Integer> vertexToRootEdgeChain = new ArrayList<>();
		Integer nextVertex = vertex;
		do {
			vertex = Integer.valueOf(nextVertex);
			nextVertex = successorIdxes[vertex];
			vertexToRootEdgeChain.add(weightedTransitions.getEdge(vertex, nextVertex));
		} while (nextVertex != rootVertex);
		return vertexToRootEdgeChain;
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
