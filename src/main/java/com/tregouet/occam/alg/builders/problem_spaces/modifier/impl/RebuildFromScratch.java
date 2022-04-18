package com.tregouet.occam.alg.builders.problem_spaces.modifier.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.alg.builders.problem_spaces.ProblemSpaceBuilder;
import com.tregouet.occam.alg.builders.problem_spaces.modifier.ProblemSpaceModifier;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.IProblemSpace;
import com.tregouet.occam.data.problem_spaces.IProblemState;

public class RebuildFromScratch implements ProblemSpaceModifier {

	public static final RebuildFromScratch INSTANCE = new RebuildFromScratch();

	private RebuildFromScratch() {
	}

	@Override
	public IProblemSpace add(IProblemSpace pbSpace, String regularExpression) {
		// NOT IMPLEMENTED YET
		return null;
	}

	@Override
	public IProblemSpace remove(IProblemSpace pbSpace, int stateID) {
		List<IProblemState> topoOrderedStates = new ArrayList<>();
		new TopologicalOrderIterator<>(pbSpace.asGraph()).forEachRemaining(topoOrderedStates::add);
		if (remove(topoOrderedStates, stateID)) {
			Set<AProblemStateTransition> transitions = ProblemSpaceModifier.getCategorizationTransitionBuilder()
					.apply(topoOrderedStates);
			return ProblemSpaceBuilder.build(topoOrderedStates, transitions);
		}
		return pbSpace;
	}

	@Override
	public IProblemSpace remove(IProblemSpace pbSpace, Set<Integer> stateIDs) {
		List<IProblemState> topoOrderedStates = new ArrayList<>();
		new TopologicalOrderIterator<>(pbSpace.asGraph()).forEachRemaining(topoOrderedStates::add);
		boolean modified = false;
		for (Integer stateID : stateIDs) {
			if (remove(topoOrderedStates, stateID))
				modified = true;
		}
		if (modified) {
			Set<AProblemStateTransition> transitions = ProblemSpaceModifier.getCategorizationTransitionBuilder()
					.apply(topoOrderedStates);
			return ProblemSpaceBuilder.build(topoOrderedStates, transitions);
		}
		return pbSpace;
	}

	private boolean remove(List<IProblemState> states, int iD) {
		ListIterator<IProblemState> ite = states.listIterator();
		while (ite.hasNext()) {
			if (ite.next().id() == iD) {
				ite.remove();
				return true;
			}
		}
		return false;
	}

	@Override
	public IProblemSpace restrictTo(IProblemSpace pbSpace, Set<Integer> stateIDs) {
		List<IProblemState> topoOrderedStates = new ArrayList<>();
		new TopologicalOrderIterator<>(pbSpace.asGraph()).forEachRemaining(topoOrderedStates::add);
		List<IProblemState> restriction = topoOrderedStates.stream().filter(s -> stateIDs.contains(s.id()))
				.collect(Collectors.toList());
		if (topoOrderedStates.equals(restriction))
			return pbSpace;
		Set<AProblemStateTransition> transitions = ProblemSpaceModifier.getCategorizationTransitionBuilder()
				.apply(restriction);
		return ProblemSpaceBuilder.build(restriction, transitions);
	}

}
