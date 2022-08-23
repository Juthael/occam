package com.tregouet.occam.alg.scorers.similarity.difference.impl;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.scorers.similarity.difference.DifferenceScorer;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;

public class SumOfDifferentiaeWeights implements DifferenceScorer {
	
	public static final SumOfDifferentiaeWeights INSTANCE = new SumOfDifferentiaeWeights();
	
	private SumOfDifferentiaeWeights() {
	}

	@Override
	public double apply(IRepresentation comparison) {
		double score = 0.0;
		DirectedAcyclicGraph<Integer, ADifferentiae> comparativeGraph = comparison.getDescription().asGraph();
		for (Integer iD : comparativeGraph.vertexSet()) {
			if (comparativeGraph.outDegreeOf(iD) == 0) {
				//then is a term of the comparison
				for (ADifferentiae diff : comparativeGraph.incomingEdgesOf(iD))
					//only one diff, since the graph is a tree
					score += diff.getCoeffFreeWeight();
			}
		}
		return score;
	}

}
