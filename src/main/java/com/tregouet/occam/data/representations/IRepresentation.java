package com.tregouet.occam.data.representations;

import java.util.Map;
import java.util.Set;

import com.tregouet.occam.data.languages.alphabets.domain_specific.IContextualizedProduction;
import com.tregouet.occam.data.languages.alphabets.generic.AVariable;
import com.tregouet.occam.data.logical_structures.automata.IPushdownAutomaton;
import com.tregouet.occam.data.logical_structures.scores.IScore;
import com.tregouet.occam.data.problem_space.partitions.IPartition;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.evaluation.tapes.IFactAsTape;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;
import com.tregouet.tree_finder.data.Tree;

public interface IRepresentation<S extends IScore<S>> extends 
	IPushdownAutomaton<
		IConcept, 
		IContextualizedProduction, 
		IFactAsTape,
		AVariable, 
		IConceptTransitionIC, 
		IConceptTransitionOIC, 
		IConceptTransition,
		IRepresentationTransitionFunction
	> {
	
	Map<Integer, Set<IFactAsTape>> mapParticularIDsToAcceptedFacts();
	
	IDescription getContextDescription();
	
	Tree<IConcept, IIsA> getTreeOfConcepts();
	
	S getScore();
	
	Set<IPartition> getPartitions();
	
	void setScore(S score);

}
