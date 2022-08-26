package com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.impl;

import java.util.Objects;
import java.util.Set;

import com.tregouet.occam.data.structures.representations.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IWeighedProperty;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.IComputation;

public class WeighedProperty implements IWeighedProperty {
	
	private final IProperty property;
	private final Set<IComputation> computations;
	private Double weight = null;
	
	/**
	 * 
	 * @param property
	 * @param computations a subset of property's computation
	 */
	public WeighedProperty(IProperty property, Set<IComputation> computations) {
		this.property = property;
		this.computations = computations;
	}

	@Override
	public Double weight() {
		return weight;
	}

	@Override
	public Set<IComputation> getComputations() {
		return computations;
	}

	@Override
	public IDenotation getFunction() {
		return property.getFunction();
	}

	@Override
	public int getGenusID() {
		return property.getGenusID();
	}

	@Override
	public int getSpeciesID() {
		return property.getSpeciesID();
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
	public int hashCode() {
		return Objects.hash(computations, property, weight);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WeighedProperty other = (WeighedProperty) obj;
		return (weight != null && other.weight != null ? Double.doubleToLongBits(weight) == Double.doubleToLongBits(other.weight) : true)
				&& Objects.equals(computations, other.computations) && Objects.equals(property, other.property);
	}

}
