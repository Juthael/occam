package com.tregouet.occam.data.problem_space.states.classifications.concepts.impl;

import java.util.Arrays;
import java.util.Set;

import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.logical_structures.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.ConceptType;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.transitions.impl.stack_default.vars.This;

public class Everything extends Concept implements IConcept {

	public Everything(Set<Integer> extentIDs) {
		super(new IConstruct[] { new Construct(Arrays.asList(new ISymbol[] { This.INSTANCE }))},
				new boolean[] {false}, extentIDs, IConcept.ONTOLOGICAL_COMMITMENT_ID);
		setType(ConceptType.ONTOLOGICAL_COMMITMENT);
	}

}
