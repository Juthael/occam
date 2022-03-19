package com.tregouet.occam.data.preconcepts.impl;

import java.util.Arrays;
import java.util.HashSet;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;
import com.tregouet.occam.data.preconcepts.IContextObject;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.representations.concepts.ConceptType;
import com.tregouet.occam.data.representations.properties.transitions.dimensions.Nothing;

public class WhatIsTherePreconcept extends Preconcept implements IPreconcept {
	
	public static final WhatIsTherePreconcept INSTANCE = new WhatIsTherePreconcept();
	
	private WhatIsTherePreconcept() {
		super(new HashSet<IConstruct>(
				Arrays.asList(
						new IConstruct[] {new Construct(
								Arrays.asList(
										new ISymbol[] {Nothing.INSTANCE}))})), 
			new HashSet<IContextObject>());
		setType(ConceptType.WHAT_IS_THERE);
	}

}
