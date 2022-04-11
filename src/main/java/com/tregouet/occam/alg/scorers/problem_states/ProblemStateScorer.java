package com.tregouet.occam.alg.scorers.problem_states;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.scorers.Scorer;
import com.tregouet.occam.data.logical_structures.orders.total.impl.LecticScore;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.IProblemState;

public interface ProblemStateScorer extends Scorer<IProblemState, LecticScore> {
	
	ProblemStateScorer setUp(DirectedAcyclicGraph<IProblemState, AProblemStateTransition> problemSpace);

}
