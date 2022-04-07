package com.tregouet.occam.data.problem_spaces;

import java.util.Map;
import java.util.Set;

import com.tregouet.occam.data.languages.alphabets.domain_specific.IContextualizedProduction;
import com.tregouet.occam.data.languages.alphabets.generic.AVariable;
import com.tregouet.occam.data.languages.words.fact.IFact;
import com.tregouet.occam.data.logical_structures.automata.IPushdownAutomaton;
import com.tregouet.occam.data.logical_structures.partial_order.PartiallyComparable;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.partitions.IPartition;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;

public interface ICategorizationState extends 
	IPushdownAutomaton<
		IConcept, 
		IContextualizedProduction, 
		IFact,
		AVariable, 
		IConceptTransitionIC, 
		IConceptTransitionOIC, 
		IConceptTransition,
		IRepresentationTransitionFunction
	>, 
	PartiallyComparable<ICategorizationState> {
	
	Map<Integer, Set<IFact>> mapParticularIDsToAcceptedFacts();
	
	Set<IPartition> getPartitions();
	
	IDescription getDescription();
	
	Set<ICategorizationGoalState> getReachableGoalStates();
	
	int id();
	
	void initializeIDGenerator();

}
