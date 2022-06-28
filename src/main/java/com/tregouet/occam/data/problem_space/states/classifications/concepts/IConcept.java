package com.tregouet.occam.data.problem_space.states.classifications.concepts;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.logical_structures.automata.states.IState;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.impl.Concept;
import com.tregouet.tree_finder.alg.unidimensional_sorting.IDichotomizable;

public interface IConcept extends IDichotomizable<IConcept>, IState {

	public static final int WHAT_IS_THERE_ID = 0;
	public static final int ONTOLOGICAL_COMMITMENT_ID = 1000;
	public static final int CONCEPT_FIRST_ID = 100;

	@Override
	boolean equals(Object obj);

	Set<IDenotation> getDenotations();

	IDenotation getMatchingDenotation(List<String> constraintAsStrings) throws IOException;

	/**
	 * A restriction may be operated on the max extent by a given classification (concept tree)
	 */
	Set<Integer> getMaxExtentIDs();

	Set<IDenotation> getRedundantDenotations();

	@Override
	int hashCode();

	boolean meets(IConstruct constraint);

	boolean meets(List<String> constraintAsStrings);

	void setType(ConceptType type);

	@Override
	String toString();

	ConceptType type();

	public static void initializeIDGenerator() {
		Concept.initializeIDGenerator();
	}

}
