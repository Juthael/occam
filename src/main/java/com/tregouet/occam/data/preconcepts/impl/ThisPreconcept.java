package com.tregouet.occam.data.preconcepts.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;
import com.tregouet.occam.data.preconcepts.IContextObject;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.representations.properties.transitions.dimensions.This;

public class ThisPreconcept extends Preconcept implements IPreconcept {
	
	public ThisPreconcept(Set<IContextObject> extent) {
		super(new HashSet<IConstruct>(
				Arrays.asList(
						new IConstruct[] {new Construct(
								Arrays.asList(
										new ISymbol[] {This.INSTANCE}))})), 
			extent);
	}

}
