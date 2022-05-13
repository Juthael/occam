package com.tregouet.occam.alg.scorers.problem_states;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.scorers.Scorer;
import com.tregouet.occam.data.logical_structures.orders.total.impl.DoubleScore;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.representations.IRepresentation;

public interface ProblemStateScorer extends Scorer<IRepresentation, DoubleScore> {

	ProblemStateScorer setUp(DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemSpace);

}
