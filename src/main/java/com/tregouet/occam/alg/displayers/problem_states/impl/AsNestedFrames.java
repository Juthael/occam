package com.tregouet.occam.alg.displayers.problem_states.impl;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.displayers.problem_states.ProblemStateDisplayer;
import com.tregouet.occam.data.problem_spaces.IProblemState;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;
import com.tregouet.occam.data.representations.ICompleteRepresentation;
import com.tregouet.occam.data.representations.IPartialRepresentation;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;

public class AsNestedFrames implements ProblemStateDisplayer {
	
	public AsNestedFrames() {
	}

	@Override
	public String apply(IProblemState state) {
		DirectedAcyclicGraph<Integer, AbstractDifferentiae> stateGraph;
		if (state instanceof IPartialRepresentation) {
			stateGraph = asGraph((IPartialRepresentation) state);
		}
		else if (state instanceof ICompleteRepresentation) {
			stateGraph = ((ICompleteRepresentation) state).getDescription().asGraph();
		}
		//there is nothing else
		else return state.toString();
		return ProblemStateDisplayer.getPartitionStringBuilder().apply(concepts, stateGraph);
	}
	
	private static DirectedAcyclicGraph<Integer, AbstractDifferentiae> asGraph(IPartialRepresentation partialRep) {
		DirectedAcyclicGraph<Integer, AbstractDifferentiae> stateDag = new DirectedAcyclicGraph<>(null, null, false);
		for (IPartition partition : partialRep.getPartitions()) {
			DirectedAcyclicGraph<Integer, AbstractDifferentiae> partitionGraph = partition.asGraph();
			//uses addEdgeWithVertices(), so no need of vertex addition
			Graphs.addAllEdges(stateDag, partitionGraph, partitionGraph.edgeSet());
		}
		return stateDag;
	}

}
