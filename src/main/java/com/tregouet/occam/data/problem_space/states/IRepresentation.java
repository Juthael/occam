package com.tregouet.occam.data.problem_space.states;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.alg.displayers.formatters.facts.FactDisplayer;
import com.tregouet.occam.alg.scorers.Scored;
import com.tregouet.occam.data.logical_structures.automata.IPushdownAutomaton;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.logical_structures.partial_order.PartiallyComparable;
import com.tregouet.occam.data.logical_structures.scores.impl.DoubleScore;
import com.tregouet.occam.data.problem_space.states.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.descriptions.IDescription;
import com.tregouet.occam.data.problem_space.states.evaluation.facts.IFact;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.problem_space.states.transitions.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.transitions.partitions.IPartition;
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
			IRepresentationTransitionFunction>,
		Scored<DoubleScore>, PartiallyComparable<IRepresentation> {

	@Override
	boolean equals(Object o);

	IDescription getDescription();

	InvertedTree<IConcept, IIsA> getTreeOfConcepts();

	@Override
	int hashCode();

	Map<Integer, Set<IFact>> mapParticularIDsToAcceptedFacts();
	
	Map<Integer, List<String>> mapParticularIDsToFactualDescription(FactDisplayer factDisplayer);
	
	Set<Integer> getExtentIDs(Integer conceptID);
	
	int iD();

	void initializeIDGenerator();
	
	boolean isFullyDeveloped();	
	
	Set<IPartition> getPartitions();

}
