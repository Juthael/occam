package com.tregouet.occam.alg.scorers.comparisons.difference.impl;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.scorers.comparisons.difference.DifferenceScorer;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;

public class DifferentiaeWeights implements DifferenceScorer {

	public static final DifferentiaeWeights INSTANCE = new DifferentiaeWeights();

	private DifferentiaeWeights() {
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
		score = score / 2;
		return score;
	}

}
