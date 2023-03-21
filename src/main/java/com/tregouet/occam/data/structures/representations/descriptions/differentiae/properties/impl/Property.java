package com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.impl;

import java.util.Objects;
import java.util.Set;

import com.tregouet.occam.data.structures.representations.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.IComputation;

public class Property implements IProperty {

	private final int genusID;
	protected final int speciesID;
	private final IDenotation function;
	protected final Set<IComputation> computations;
	private int nbOfSignificantComp;
	private Double weight = null;

	public Property(int genusID, int speciesID, IDenotation function, Set<IComputation> computations, int nbOfSignificantComp) {
		this.genusID = genusID;
		this.speciesID = speciesID;
		this.function = function;
		this.computations = computations;
		this.nbOfSignificantComp = nbOfSignificantComp;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Property other = (Property) obj;
		return Objects.equals(computations, other.computations) && Objects.equals(function, other.function)
				&& speciesID == other.speciesID;
	}

	@Override
	public Set<IComputation> getComputations() {
		return computations;
	}

	@Override
	public IDenotation getFunction() {
		return function;
	}

	@Override
	public int getGenusID() {
		return genusID;
	}

	@Override
	public int getNbOfSignificantComputations() {
		return nbOfSignificantComp;
	}

	@Override
	public int getSpeciesID() {
		return speciesID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(computations, function, speciesID);
	}

	@Override
	public boolean isBlank() {
		return nbOfSignificantComp == 0;
	}

	@Override
	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public Double weight() {
		return weight;
	}

	@Override
	public boolean isRelational() {
		return false;
	}

}
