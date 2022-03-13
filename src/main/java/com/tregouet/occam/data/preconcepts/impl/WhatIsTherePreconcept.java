package com.tregouet.occam.data.preconcepts.impl;

import java.util.Arrays;
import java.util.HashSet;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.concepts.ConceptType;
import com.tregouet.occam.data.concepts.transitions.dimensions.Nothing;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;
import com.tregouet.occam.data.preconcepts.IContextObject;
import com.tregouet.occam.data.preconcepts.IPreconcept;

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
