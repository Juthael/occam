package com.tregouet.occam.data.representations.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.tregouet.occam.data.logical_structures.scores.impl.LexicographicScore;
import com.tregouet.occam.data.problem_spaces.ICategorizationGoalState;
import com.tregouet.occam.data.representations.ICompleteRepresentation;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.partitions.IPartition;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;
import com.tregouet.tree_finder.data.InvertedTree;

public class CompleteRepresentation extends Representation implements ICompleteRepresentation {
	
	private LexicographicScore score = null;
	
	public CompleteRepresentation(InvertedTree<IConcept, IIsA> classification, IDescription description, 
			IRepresentationTransitionFunction transitionFunction, Set<IPartition> partitions) {
		super(classification, description, transitionFunction, partitions);
	}

	@Override
	public void setScore(LexicographicScore score) {
		this.score = score;
	}

	@Override
	public LexicographicScore score() {
		return score;
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
	public int compareTo(ICompleteRepresentation other) {
		int scoreComparison = this.score.compareTo(other.score());
		if (scoreComparison == 0 && !this.equals(other))
			return System.identityHashCode(this) - System.identityHashCode(other);
		return scoreComparison;
	}

	@Override
	public Set<ICategorizationGoalState> getReachableGoalStates() {
		return new HashSet<>(Arrays.asList(new CompleteRepresentation[] {this}));
	}

	@Override
	public Set<ICompleteRepresentation> getRepresentationCompletions() {
		return new HashSet<>(Arrays.asList(new CompleteRepresentation[] {this}));
	}

}
