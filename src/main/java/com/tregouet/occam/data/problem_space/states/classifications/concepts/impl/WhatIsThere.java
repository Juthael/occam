package com.tregouet.occam.data.problem_space.states.classifications.concepts.impl;

import java.util.Arrays;
import java.util.HashSet;

import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.logical_structures.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.ConceptType;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.transitions.impl.stack_default.vars.Nothing;

public class WhatIsThere extends Concept implements IConcept {

	public static final WhatIsThere INSTANCE = new WhatIsThere();

	private WhatIsThere() {
		super(new IConstruct[] { new Construct(Arrays.asList(new ISymbol[] { Nothing.INSTANCE })) }, new boolean[] {false},
				new HashSet<Integer>(), IConcept.WHAT_IS_THERE_ID);
		setType(ConceptType.WHAT_IS_THERE);
	}

}
