package com.tregouet.occam.alg.builders.pb_space.impl;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.pb_space.ProblemSpaceExplorer;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;

public class SparseReducer extends NormalizeClassificationThenBuildProductions implements ProblemSpaceExplorer {
	
	public SparseReducer() {
		super();
	}
	
	protected static void reduce(DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph) {
		//build reduced sparse graph
		DirectedAcyclicGraph<Integer, DefaultEdge> sparseGraph = new DirectedAcyclicGraph<>(null, DefaultEdge::new, false);
		for (IRepresentation representation : problemGraph.vertexSet())
			sparseGraph.addVertex(representation.iD());
		for (AProblemStateTransition transition : problemGraph.edgeSet())
			sparseGraph.addEdge(problemGraph.getEdgeSource(transition).iD(), problemGraph.getEdgeTarget(transition).iD());
		TransitiveReduction.INSTANCE.reduce(sparseGraph);
		//remove transitions
		Set<AProblemStateTransition> removableTransitions = new HashSet<>();
		for (AProblemStateTransition transition : problemGraph.edgeSet()) {
			if (!sparseGraph.containsEdge(problemGraph.getEdgeSource(transition).iD(), problemGraph.getEdgeTarget(transition).iD()))
				removableTransitions.add(transition);
		}
		problemGraph.removeAllEdges(removableTransitions);
	}

}
