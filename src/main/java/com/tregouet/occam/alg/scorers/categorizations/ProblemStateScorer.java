package com.tregouet.occam.alg.scorers.categorizations;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.modules.sorting.transitions.AProblemStateTransition;
import com.tregouet.occam.data.structures.representations.IRepresentation;

public interface ProblemStateScorer {

	double score(IRepresentation representation);

	ProblemStateScorer setUp(DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemSpace);

}
