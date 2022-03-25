package com.tregouet.occam.data.concepts.impl;

import java.util.Arrays;
import java.util.HashSet;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.concepts.ConceptType;
import com.tregouet.occam.data.concepts.IContextObject;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;
import com.tregouet.occam.data.representations.properties.transitions.dimensions.Nothing;

public class WhatIsThere extends Concept implements IConcept {
	
	public static final WhatIsThere INSTANCE = new WhatIsThere();
	
	private WhatIsThere() {
		super(new HashSet<IConstruct>(
				Arrays.asList(
						new IConstruct[] {new Construct(
								Arrays.asList(
										new ISymbol[] {Nothing.INSTANCE}))})), 
			new HashSet<IContextObject>());
		setType(ConceptType.WHAT_IS_THERE);
	}

}
