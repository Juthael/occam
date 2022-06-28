package com.tregouet.occam.alg.scorers.problem_states;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;

public interface ProblemStateScorer {

	double score(IRepresentation representation);

	ProblemStateScorer setUp(DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemSpace);

}
