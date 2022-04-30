package com.tregouet.occam.data.representations.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.data.problem_spaces.IGoalState;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;
import com.tregouet.occam.data.representations.ICompleteRepresentation;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.occam.data.representations.evaluation.IFactEvaluator;
import com.tregouet.tree_finder.data.InvertedTree;
import com.tregouet.tree_finder.data.Tree;

public class CompleteRepresentation extends Representation implements ICompleteRepresentation {

	public CompleteRepresentation(InvertedTree<IConcept, IIsA> classification, IDescription description,
			IFactEvaluator factEvaluator, Set<IPartition> partitions) {
		super(classification, description, factEvaluator, partitions);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		CompleteRepresentation other = (CompleteRepresentation) obj;
		return Objects.equals(description, other.description) && Objects.equals(classification, other.classification);
	}

	@Override
	public Set<IGoalState> getReachableGoalStates() {
		return new HashSet<>(Arrays.asList(new CompleteRepresentation[] { this }));
	}

	@Override
	public Set<ICompleteRepresentation> getRepresentationCompletions() {
		return new HashSet<>(Arrays.asList(new CompleteRepresentation[] { this }));
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, classification);
	}

	@Override
	public Set<Integer> getExtent(Integer conceptID) {
		Tree<Integer, AbstractDifferentiae> descriptionAsGraph = description.asGraph();
		Set<Integer> particularIDs = descriptionAsGraph.getLeaves();
		if (particularIDs.contains(conceptID))
			return new HashSet<>(Arrays.asList(new Integer[] {conceptID}));
		if (!descriptionAsGraph.containsVertex(conceptID))
			return null;
		return new HashSet<>(Sets.difference(descriptionAsGraph.getDescendants(conceptID), particularIDs));
	}

}
