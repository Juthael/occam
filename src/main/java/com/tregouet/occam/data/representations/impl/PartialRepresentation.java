package com.tregouet.occam.data.representations.impl;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.data.logical_structures.orders.total.impl.LecticScore;
import com.tregouet.occam.data.problem_spaces.IGoalState;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;
import com.tregouet.occam.data.representations.ICompleteRepresentation;
import com.tregouet.occam.data.representations.IPartialRepresentation;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;
import com.tregouet.tree_finder.data.InvertedTree;

public class PartialRepresentation extends Representation implements IPartialRepresentation {

	private Set<ICompleteRepresentation> representationCompletions;
	
	public PartialRepresentation(Set<IPartition> partitions, Set<ICompleteRepresentation> representationCompletions) {
		super(null, null, null, partitions);
		this.representationCompletions = representationCompletions;
	}
	
	@Override
	public void setClassification(InvertedTree<IConcept, IIsA> classification) {
		super.classification = classification;
	}

	@Override
	public void setUpFactEvaluator(IRepresentationTransitionFunction transFunc) {
		super.factEvaluator.set(transFunc);

	}

	@Override
	public void setDescription(IDescription description) {
		super.description = description;
	}

	@Override
	public Set<IGoalState> getReachableGoalStates() {
		return new HashSet<>(representationCompletions);
	}
	
	@Override
	public Set<ICompleteRepresentation> getRepresentationCompletions() {
		return representationCompletions;
	}

	@Override
	public LecticScore score() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setScore(LecticScore score) {
		// TODO Auto-generated method stub
		
	}	

}
