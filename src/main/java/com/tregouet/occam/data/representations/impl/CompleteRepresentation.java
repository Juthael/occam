package com.tregouet.occam.data.representations.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.tregouet.occam.data.problem_spaces.IGoalState;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;
import com.tregouet.occam.data.representations.ICompleteRepresentation;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;
import com.tregouet.tree_finder.data.InvertedTree;

public class CompleteRepresentation extends Representation implements ICompleteRepresentation {
	
	public CompleteRepresentation(InvertedTree<IConcept, IIsA> classification, IDescription description, 
			IRepresentationTransitionFunction transitionFunction, Set<IPartition> partitions) {
		super(classification, description, transitionFunction, partitions);
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, classification);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompleteRepresentation other = (CompleteRepresentation) obj;
		return Objects.equals(description, other.description) && Objects.equals(classification, other.classification);
	}

	@Override
	public Set<IGoalState> getReachableGoalStates() {
		return new HashSet<>(Arrays.asList(new CompleteRepresentation[] {this}));
	}

	@Override
	public Set<ICompleteRepresentation> getRepresentationCompletions() {
		return new HashSet<>(Arrays.asList(new CompleteRepresentation[] {this}));
	}

}
