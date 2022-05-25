package com.tregouet.occam.alg.scorers.problem_states;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.scorers.Scorer;
import com.tregouet.occam.data.logical_structures.scores.impl.DoubleScore;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;

public interface ProblemStateScorer extends Scorer<IRepresentation, DoubleScore> {

	ProblemStateScorer setUp(DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemSpace);

}
