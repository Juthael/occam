package com.tregouet.occam.data.representations.concepts.impl;

import java.util.Arrays;
import java.util.HashSet;

import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.logical_structures.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.representations.concepts.ConceptType;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.transitions.dimensions.Nothing;

public class WhatIsThere extends Concept implements IConcept {

	public static final WhatIsThere INSTANCE = new WhatIsThere();

	private WhatIsThere() {
		super(new HashSet<>(
				Arrays.asList(new IConstruct[] { new Construct(Arrays.asList(new ISymbol[] { Nothing.INSTANCE })) })),
				new HashSet<IContextObject>(), IConcept.WHAT_IS_THERE_ID);
		setType(ConceptType.WHAT_IS_THERE);
	}

}
