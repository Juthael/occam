package com.tregouet.occam.data.representations.concepts.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.data.languages.alphabets.ISymbol;
import com.tregouet.occam.data.languages.words.construct.IConstruct;
import com.tregouet.occam.data.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.representations.concepts.ConceptType;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.properties.transitions.dimensions.This;

public class Everything extends Concept implements IConcept {
	
	public Everything(Set<IContextObject> extent) {
		super(new HashSet<IConstruct>(
				Arrays.asList(
						new IConstruct[] {new Construct(
								Arrays.asList(
										new ISymbol[] {This.INSTANCE}))})), 
			extent);
		setType(ConceptType.ONTOLOGICAL_COMMITMENT);
	}

}