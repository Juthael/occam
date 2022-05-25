package com.tregouet.occam.data.problem_space.states.concepts.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;
import com.tregouet.occam.data.logical_structures.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.problem_space.states.concepts.ConceptType;
import com.tregouet.occam.data.problem_space.states.concepts.IComplementaryConcept;
import com.tregouet.occam.data.problem_space.states.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.concepts.denotations.IDenotation;

public class ComplementaryConcept extends Concept implements IComplementaryConcept {

	private final IConcept complementedByThis;
	private IConcept wrappedComplementing = null;

	public ComplementaryConcept(IConcept toBeComplemented, IConcept complementing) {
		super(complementing.getDenotations().stream().map(d -> new Construct(d.asList())).collect(Collectors.toSet()),
				new HashSet<>(Sets.difference(complementing.getExtentIDs(), toBeComplemented.getExtentIDs())),
				-toBeComplemented.iD());
		complementedByThis = toBeComplemented;
		wrappedComplementing = complementing;
		setType(ConceptType.UNIVERSAL);
	}

	public ComplementaryConcept(IConcept toBeComplemented, IConcept supremum, Set<Integer> extentIDs) {
		super(supremum.getDenotations().stream().map(d -> new Construct(d.asList())).collect(Collectors.toSet()),
				extentIDs, -toBeComplemented.iD());
		complementedByThis = toBeComplemented;
		setType(ConceptType.UNIVERSAL);
	}

	@Override
	public IConcept buildComplementOfThis(Set<IConcept> complementMinimalLowerBounds, IConcept supremum) {
		// already a complement
		return null;
	}

	@Override
	public IConcept complementThisWith(IConcept complementing) {
		// already a complement
		return null;
	}

	@Override
	public IConcept getComplemented() {
		return complementedByThis;
	}

	@Override
	public IConcept getWrappedComplementing() {
		return wrappedComplementing;
	}

	@Override
	public boolean isComplementary() {
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		String nL = System.lineSeparator();
		sB.append(Integer.toString(-complementedByThis.iD()) + nL);
		Iterator<IDenotation> iterator = getDenotations().iterator();
		while (iterator.hasNext()) {
			sB.append(iterator.next().toString());
			if (iterator.hasNext())
				sB.append(nL);
		}
		return sB.toString();
	}	

}
