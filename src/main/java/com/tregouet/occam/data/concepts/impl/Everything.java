package com.tregouet.occam.data.concepts.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.concepts.ConceptType;
import com.tregouet.occam.data.concepts.IContextObject;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;
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
