package com.tregouet.occam.data.problem_spaces.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.data.languages.words.fact.IFact;
import com.tregouet.occam.data.problem_spaces.ICategorizationGoalState;
import com.tregouet.occam.data.problem_spaces.ICategorizationState;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.partitions.IPartition;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;

public class PartialRepresentation implements ICategorizationState {

	@Override
	public Collection<IConcept> getStates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRepresentationTransitionFunction getTransitionFunction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean evaluate(IFact word) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<IFact> enumerateMachineLanguage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<IConcept, Set<IFact>> acceptStateToAcceptedWords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<IConcept, Set<IFact>> anyStateToAcceptedWords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer compareTo(ICategorizationState o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, Set<IFact>> mapParticularIDsToAcceptedFacts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IPartition> getPartitions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDescription getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<ICategorizationGoalState> getReacheableGoalStates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int id() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void initializeIDGenerator() {
		// TODO Auto-generated method stub

	}

}
