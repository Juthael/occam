package com.tregouet.occam.data.representations;

import java.util.Map;
import java.util.Set;

import com.tregouet.occam.alg.scorers.Scored;
import com.tregouet.occam.data.logical_structures.automata.IPushdownAutomaton;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.logical_structures.orders.total.impl.LecticScore;
import com.tregouet.occam.data.problem_spaces.IProblemState;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.evaluation.facts.IFact;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;
import com.tregouet.tree_finder.data.InvertedTree;

public interface IRepresentation
	extends
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
		IProblemState,
		Scored<LecticScore>,
		Comparable<IRepresentation>{

	@Override
	boolean equals(Object o);

	IDescription getDescription();

	Set<ICompleteRepresentation> getRepresentationCompletions();

	InvertedTree<IConcept, IIsA> getTreeOfConcepts();

	@Override
	int hashCode();

	Map<Integer, Set<IFact>> mapParticularIDsToAcceptedFacts();

}
