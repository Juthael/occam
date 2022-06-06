package com.tregouet.occam.data.problem_space.states.classifications.concepts.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.ConceptType;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IComplementaryConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;

public class ComplementaryConcept extends Concept implements IComplementaryConcept {

	private final IConcept complementedByThis;
	private Integer wrappedComplementingID = null;

	public ComplementaryConcept(IConcept toBeComplemented, IConcept complementing) {
		super(new ArrayList<>(complementing.getDenotations()), new HashSet<>(Sets.difference(complementing.getMaxExtentIDs(), 
				toBeComplemented.getMaxExtentIDs())), -toBeComplemented.iD());
		complementedByThis = toBeComplemented;
		wrappedComplementingID = complementing.iD();
		setType(ConceptType.UNIVERSAL);
	}

	public ComplementaryConcept(IConcept toBeComplemented, IConcept supremum, Set<Integer> extentIDs) {
		super(new ArrayList<IDenotation>(supremum.getDenotations()), extentIDs, -toBeComplemented.iD());
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
	public Integer getWrappedComplementingID() {
		return wrappedComplementingID;
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
