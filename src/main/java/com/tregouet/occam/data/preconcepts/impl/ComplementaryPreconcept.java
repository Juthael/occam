package com.tregouet.occam.data.preconcepts.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;
import com.tregouet.occam.data.languages.generic.impl.Construct;
import com.tregouet.occam.data.preconcepts.IComplementaryPreconcept;
import com.tregouet.occam.data.preconcepts.IContextObject;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.representations.ConceptType;

public class ComplementaryPreconcept extends Preconcept implements IComplementaryPreconcept {

	private final IPreconcept complementedByThis;
	private IPreconcept wrappedComplementing = null;
	
	public ComplementaryPreconcept(IPreconcept toBeComplemented, IPreconcept complementing) {
		super(complementing.getDenotations().stream()
				.map(d -> new Construct(d.getListOfSymbols()))
				.collect(Collectors.toSet()), 
				new HashSet<>(Sets.difference(complementing.getExtent(), toBeComplemented.getExtent())), 
				complementing.getID());
		complementedByThis = toBeComplemented;
		wrappedComplementing = complementing;
		setType(ConceptType.UNIVERSAL);
	}
	
	public ComplementaryPreconcept(IPreconcept toBeComplemented, IPreconcept supremum, Set<IContextObject> extent) {
		super(supremum.getDenotations().stream()
				.map(d -> new Construct(d.getListOfSymbols()))
				.collect(Collectors.toSet()), 
				extent);
		complementedByThis = toBeComplemented;
		setType(ConceptType.UNIVERSAL);
	}	
	
	@Override
	public IPreconcept buildComplementOfThis(Set<IPreconcept> complementMinimalLowerBounds, IPreconcept supremum) {
		//already a complement
		return null;
	}	
	
	@Override
	public IPreconcept complementThisWith(IPreconcept complementing) {
		//already a complement
		return null;
	}	
	
	@Override
	public IPreconcept getComplemented() {
		return complementedByThis;
	}

	@Override
	public boolean isComplementary() {
		return true;
	}

	@Override
	public String toString() {
		return Integer.toString(-complementedByThis.getID());
	}

	@Override
	public IPreconcept getWrappedComplementing() {
		return wrappedComplementing;
	}	

}
