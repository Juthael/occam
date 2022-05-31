package com.tregouet.occam.data.problem_space.states.concepts;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.logical_structures.automata.states.IState;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.problem_space.states.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.concepts.impl.Concept;
import com.tregouet.tree_finder.alg.unidimensional_sorting.IDichotomizable;

public interface IConcept extends IDichotomizable<IConcept>, IState {

	public static final int WHAT_IS_THERE_ID = 0;
	public static final int ONTOLOGICAL_COMMITMENT_ID = 1000;
	public static final int CONCEPT_FIRST_ID = 100;

	@Override
	boolean equals(Object obj);

	Set<IDenotation> getDenotations();

	Set<Integer> getExtentIDs();

	IDenotation getMatchingDenotation(List<String> constraintAsStrings) throws IOException;

	Set<IDenotation> getRedundantDenotations();

	@Override
	int hashCode();

	public static void initializeIDGenerator() {
		Concept.initializeIDGenerator();
	}

	boolean meets(IConstruct constraint);

	boolean meets(List<String> constraintAsStrings);

	void setType(ConceptType type);

	@Override
	String toString();

	ConceptType type();

}
