package com.tregouet.occam.data.preconcepts.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.concepts.transitions.dimensions.This;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;
import com.tregouet.occam.data.preconcepts.IContextObject;
import com.tregouet.occam.data.preconcepts.IPreconcept;

public class TruismPreconcept extends Preconcept implements IPreconcept {
	
	public TruismPreconcept(Set<IContextObject> extent) {
		super(new HashSet<IConstruct>(
				Arrays.asList(
						new IConstruct[] {new Construct(
								Arrays.asList(
										new ISymbol[] {This.INSTANCE}))})), 
			extent);
	}

}
