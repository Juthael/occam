package com.tregouet.occam.data.structures.representations.classifications.concepts.impl;

import java.util.Arrays;
import java.util.HashSet;

import com.tregouet.occam.data.structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.structures.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.structures.representations.classifications.concepts.ConceptType;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.structures.representations.transitions.impl.stack_default.vars.Nothing;

public class WhatIsThere extends Concept implements IConcept {

	public static final WhatIsThere INSTANCE = new WhatIsThere();

	private WhatIsThere() {
		super(new IConstruct[] { new Construct(Arrays.asList(new ISymbol[] { Nothing.INSTANCE })) }, new boolean[] {false},
				new HashSet<Integer>(), IConcept.WHAT_IS_THERE_ID);
		setType(ConceptType.WHAT_IS_THERE);
	}

}
