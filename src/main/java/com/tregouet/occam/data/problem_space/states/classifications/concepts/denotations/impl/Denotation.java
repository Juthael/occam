package com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.impl;

import java.util.Objects;

import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.logical_structures.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;

public class Denotation extends Construct implements IDenotation {

	private final int conceptID;
	private boolean isRedundant = false;

	public Denotation(IConstruct construct, int conceptID) {
		super(construct);
		this.conceptID = conceptID;
	}

	@Override
	public Integer compareTo(IDenotation other) {
		return DenotationComparator.INSTANCE.compare(this, other);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj) || (getClass() != obj.getClass()))
			return false;
		Denotation other = (Denotation) obj;
		return conceptID == other.conceptID;
	}

	@Override
	public int getConceptID() {
		return conceptID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(conceptID);
		return result;
	}

	@Override
	public boolean isRedundant() {
		return isRedundant;
	}

	@Override
	public void markAsRedundant() {
		isRedundant = true;
	}

}
