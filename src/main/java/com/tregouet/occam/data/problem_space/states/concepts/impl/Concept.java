package com.tregouet.occam.data.problem_space.states.concepts.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.logical_structures.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.problem_space.states.concepts.ConceptType;
import com.tregouet.occam.data.problem_space.states.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.concepts.denotations.impl.Denotation;

public class Concept implements IConcept {

	protected static int nextID = IConcept.CONCEPT_FIRST_ID;

	private final Set<IDenotation> denotations = new HashSet<>();
	private final Set<Integer> extentIDs;
	private final int iD;
	private ConceptType type;

	public Concept(Set<IConstruct> denotatingConstructs, Set<Integer> extentIDs) {
		if (extentIDs.size() == 1)
			iD = new ArrayList<>(extentIDs).get(0);
		else
			iD = nextID++;
		for (IConstruct construct : denotatingConstructs)
			this.denotations.add(new Denotation(construct, this.iD));
		this.extentIDs = extentIDs;
	}

	public Concept(Set<IConstruct> denotatingConstructs, Set<Integer> extentIDs, int iD) {
		this.iD = iD;
		for (IConstruct construct : denotatingConstructs)
			this.denotations.add(new Denotation(construct, this.iD));
		this.extentIDs = extentIDs;
	}

	@Override
	public IConcept buildComplementOfThis(Set<IConcept> complementMinimalLowerBounds, IConcept supremum) {
		Set<Integer> complementExtent = new HashSet<>();
		for (IConcept complementMinLowerBound : complementMinimalLowerBounds)
			complementExtent.addAll(complementMinLowerBound.getExtentIDs());
		return new ComplementaryConcept(this, supremum, complementExtent);
	}

	@Override
	public IConcept complementThisWith(IConcept complementing) {
		return new ComplementaryConcept(this, complementing);
	}

	@Override
	public IConcept getComplemented() {
		return null;
	}

	@Override
	public Set<IDenotation> getDenotations() {
		return denotations;
	}

	@Override
	public Set<Integer> getExtentIDs() {
		return extentIDs;
	}

	/**
	 * If many attributes meet the constraint, returns the first found.
	 * 
	 * @throws PropertyTargetingException
	 */
	@Override
	public IDenotation getMatchingDenotation(List<String> constraintAsStrings) throws IOException {
		IDenotation matchingDenotation = null;
		IConstruct constraintAsConstruct = new Construct(
				constraintAsStrings.toArray(new String[constraintAsStrings.size()]));
		Iterator<IDenotation> denotationIte = getDenotations().iterator();
		while (denotationIte.hasNext()) {
			IDenotation currDenotation = denotationIte.next();
			if (currDenotation.meets(constraintAsConstruct)) {
				if (matchingDenotation == null)
					matchingDenotation = currDenotation;
				else
					throw new IOException("AbstractDenotationSet.getMatchingDenotation(List<String>) : "
							+ "the constraint is not specific enough to target a single attribute.");
			}
		}
		return matchingDenotation;
	}

	@Override
	public Set<IDenotation> getRedundantDenotations() {
		Set<IDenotation> redundantDenotations = new HashSet<>();
		for (IDenotation denotation : denotations) {
			if (denotation.isRedundant())
				redundantDenotations.add(denotation);
		}
		return redundantDenotations;
	}

	@Override
	public int iD() {
		return iD;
	}

	public static void initializeIDGenerator() {
		nextID = IConcept.CONCEPT_FIRST_ID;
	}

	@Override
	public boolean isComplementary() {
		return false;
	}

	@Override
	public boolean meets(IConstruct constraint) {
		return getDenotations().stream().anyMatch(a -> a.meets(constraint));
	}

	@Override
	public boolean meets(List<String> constraintAsStrings) {
		IConstruct constraint = new Construct(constraintAsStrings.toArray(new String[constraintAsStrings.size()]));
		return meets(constraint);
	}

	@Override
	public void setType(ConceptType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		if (type == ConceptType.ABSURDITY)
			return "ABSURDITY";
		StringBuilder sB = new StringBuilder();
		sB.append(Integer.toString(iD));
		String newLine = System.lineSeparator();
		sB.append(newLine);
		Iterator<IDenotation> iterator = denotations.iterator();
		while (iterator.hasNext()) {
			sB.append(iterator.next().toString());
			if (iterator.hasNext())
				sB.append(newLine);
		}
		return sB.toString();
	}

	@Override
	public ConceptType type() {
		return type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(denotations, extentIDs, iD, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Concept other = (Concept) obj;
		return iD == other.iD && type == other.type && Objects.equals(denotations, other.denotations) 
				&& Objects.equals(extentIDs, other.extentIDs);
	}

}
