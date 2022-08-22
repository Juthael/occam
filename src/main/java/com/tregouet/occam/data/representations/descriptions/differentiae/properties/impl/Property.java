package com.tregouet.occam.data.representations.descriptions.differentiae.properties.impl;

import java.util.Objects;
import java.util.Set;

import com.tregouet.occam.data.representations.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.representations.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.representations.descriptions.differentiae.properties.computations.IComputation;

public class Property implements IProperty {

	private final int genusID;
	private final int speciesID;
	private final IDenotation function;
	private final Set<IComputation> computations;
	private Double weight = null;

	public Property(int genusID, int speciesID, IDenotation function, Set<IComputation> computations) {
		this.genusID = genusID;
		this.speciesID = speciesID;
		this.function = function;
		this.computations = computations;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Property other = (Property) obj;
		return speciesID == other.speciesID && genusID == other.genusID &&
				Objects.equals(computations, other.computations) && Objects.equals(function, other.function);
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
	public int getSpeciesID() {
		return speciesID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(computations, function, genusID, speciesID);
	}

	@Override
	public boolean isBlank() {
		for (IComputation computation : computations) {
			if (!computation.isIdentity())
				return false;
		}
		return true;
	}

	@Override
	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public Double weight() {
		return weight;
	}

}
