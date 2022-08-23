package com.tregouet.occam.data.structures.representations.classifications.concepts.impl;

import java.util.Arrays;
import java.util.Set;

import com.tregouet.occam.data.structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.structures.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.structures.representations.classifications.concepts.ConceptType;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.structures.representations.transitions.impl.stack_default.vars.This;

public class Everything extends Concept implements IConcept {

	public Everything(Set<Integer> extentIDs) {
		super(new IConstruct[] { new Construct(Arrays.asList(new ISymbol[] { This.INSTANCE }))},
				new boolean[] {false}, extentIDs, IConcept.ONTOLOGICAL_COMMITMENT_ID);
		setType(ConceptType.ONTOLOGICAL_COMMITMENT);
	}

}
