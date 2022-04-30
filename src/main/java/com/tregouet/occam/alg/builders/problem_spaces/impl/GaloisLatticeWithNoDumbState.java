package com.tregouet.occam.alg.builders.problem_spaces.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.builders.problem_spaces.ProblemSpaceBuilder;
import com.tregouet.occam.alg.builders.problem_spaces.partial_representations.PartialRepresentationLateSetter;
import com.tregouet.occam.alg.builders.problem_spaces.utils.ProblemSpaceGraphInstantiator;
import com.tregouet.occam.alg.scorers.problem_states.ProblemStateScorer;
import com.tregouet.occam.data.logical_structures.orders.total.impl.LecticScore;
import com.tregouet.occam.data.logical_structures.orders.total.impl.Size1LecticScore;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.IProblemSpace;
import com.tregouet.occam.data.problem_spaces.IProblemState;
import com.tregouet.occam.data.problem_spaces.impl.ProblemSpace;
import com.tregouet.tree_finder.utils.Functions;

public class GaloisLatticeWithNoDumbState extends GaloisLatticeOfRepresentations implements ProblemSpaceBuilder {
	
	@Override
	protected IProblemSpace instantiate(List<IProblemState> topoOrderedStates, Set<AProblemStateTransition> transitions) {
		DirectedAcyclicGraph<IProblemState, AProblemStateTransition> problemGraph = 
				ProblemSpaceGraphInstantiator.INSTANCE.apply(topoOrderedStates, transitions);
		ProblemStateScorer scorer = ProblemSpaceBuilder.getProblemStateScorer().setUp(problemGraph);
		for (IProblemState state : problemGraph)
			state.setScore(scorer.apply(state));
		removeDumbStates(problemGraph);
		PartialRepresentationLateSetter partialRepLateSetter = ProblemSpaceBuilder.getPartialRepresentationLateSetter();
		return new ProblemSpace(problemGraph, partialRepLateSetter);
	}
	
	private static void removeDumbStates(DirectedAcyclicGraph<IProblemState, AProblemStateTransition> problemGraph) {
		List<IProblemState> states = new ArrayList<>();
		new TopologicalOrderIterator<>(problemGraph).forEachRemaining(states::add);
		LecticScore zero = new Size1LecticScore(0.0);
		for (int i = 0 ; i < states.size() ; i++) {
			IProblemState iState = states.get(i);
			if (iState.score().equals(zero)) {
				Set<IProblemState> upperSet = Functions.upperSet(problemGraph, iState);
				states.removeAll(upperSet);
				i--;
			}
		}
		Set<IProblemState> dumbSet = new HashSet<>(Sets.difference(problemGraph.vertexSet(), new HashSet<>(states)));
		problemGraph.removeAllVertices(dumbSet);
	}

}
