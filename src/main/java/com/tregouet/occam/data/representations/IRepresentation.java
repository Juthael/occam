package com.tregouet.occam.data.representations;

import java.util.Map;
import java.util.Set;

import com.tregouet.occam.alg.scorers.Scored;
import com.tregouet.occam.data.languages.alphabets.domain_specific.IContextualizedProduction;
import com.tregouet.occam.data.languages.alphabets.generic.AVariable;
import com.tregouet.occam.data.languages.words.fact.IFact;
import com.tregouet.occam.data.logical_structures.automata.IPushdownAutomaton;
import com.tregouet.occam.data.logical_structures.scores.impl.LexicographicScore;
import com.tregouet.occam.data.problem_space.partitions.IPartition;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;
import com.tregouet.tree_finder.data.InvertedTree;

public interface IRepresentation extends 
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
	Scored<LexicographicScore>, 
	Comparable<IRepresentation> {
	
	Map<Integer, Set<IFact>> mapParticularIDsToAcceptedFacts();
	
	IDescription getContextDescription();
	
	InvertedTree<IConcept, IIsA> getTreeOfConcepts();
	
	Set<IPartition> getPartitions();
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object o);

}
