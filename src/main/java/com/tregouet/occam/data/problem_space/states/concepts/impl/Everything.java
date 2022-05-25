package com.tregouet.occam.data.problem_space.states.concepts.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.logical_structures.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.problem_space.states.concepts.ConceptType;
import com.tregouet.occam.data.problem_space.states.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.transitions.dimensions.This;

public class Everything extends Concept implements IConcept {

	public Everything(Set<Integer> extentIDs) {
		super(new HashSet<>(
				Arrays.asList(new IConstruct[] { new Construct(Arrays.asList(new ISymbol[] { This.INSTANCE })) })),
				extentIDs, IConcept.ONTOLOGICAL_COMMITMENT_ID);
		setType(ConceptType.ONTOLOGICAL_COMMITMENT);
	}

}
